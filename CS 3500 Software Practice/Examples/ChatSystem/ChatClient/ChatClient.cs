using System;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Text.RegularExpressions;

namespace ChatClient
{
  // TODO: Should we really declare SocketState in both places (client and server)?
  //       No! This will be fixed in PS7.
  class SocketState
  {
    public Socket theSocket;
    public byte[] messageBuffer;
    public StringBuilder sb;

    public SocketState(Socket s)
    {
      theSocket = s;
      messageBuffer = new byte[1024];
      sb = new StringBuilder();
    }
  }

  class ChatClient
  {
    private const int port = 11000;

    static void Main(string[] args)
    {
      ChatClient client = new ChatClient();

      Console.WriteLine("enter server address:");
      string serverAddr = Console.ReadLine();

      client.ConnectToServer(serverAddr);

      // Hold the client open, since the work is done on other threads
      // TODO: Left as an exercise, there are better ways to do this
      System.Threading.Thread.Sleep(1000000);
    }


    /// <summary>
    /// Starts the connection process
    /// </summary>
    /// <param name="serverAddr"></param>
    private void ConnectToServer(string serverAddr)
    {
      // Parse the IP
      IPAddress addr = IPAddress.Parse(serverAddr);

      Socket s = new Socket(
        addr.AddressFamily,
        SocketType.Stream,
        ProtocolType.Tcp);

      SocketState state = new SocketState(s);

      // Connect
      state.theSocket.BeginConnect(addr, port, OnConnected, state);
    }

    /// <summary>
    /// Callback for when a connection is made (see line 61)
    /// Finalizes the connection, then starts a receive loop.
    /// </summary>
    /// <param name="ar"></param>
    private void OnConnected(IAsyncResult ar)
    {
      Console.WriteLine("contact from server");

      SocketState state = (SocketState)ar.AsyncState;

      state.theSocket.EndConnect(ar);

      // Start a receive loop
      state.theSocket.BeginReceive(state.messageBuffer, 0, state.messageBuffer.Length,
        SocketFlags.None, OnReceive, state);

      // Now that we know we are connected, we can start sending messages
      SendMessages(state.theSocket);
    }


    /// <summary>
    /// Callback for when a receive operation completes
    /// </summary>
    /// <param name="ar"></param>
    private void OnReceive(IAsyncResult ar)
    {
      SocketState state = (SocketState)ar.AsyncState;

      int numBytes = state.theSocket.EndReceive(ar);

      string message = Encoding.UTF8.GetString(state.messageBuffer,
        0, numBytes);

      state.sb.Append(message);

      ProcessMessages(state.sb);

      // Continue the event loop started on line 78 and receive more data
      state.theSocket.BeginReceive(state.messageBuffer, 0, state.messageBuffer.Length,
        SocketFlags.None, OnReceive, state);
    }


    /// <summary>
    /// Look for complete messages (terminated by a '.'), 
    /// then print and remove them from the string builder.
    /// </summary>
    /// <param name="sb"></param>
    private void ProcessMessages(StringBuilder sb)
    {
      string totalData = sb.ToString();
      string[] parts = Regex.Split(totalData, @"(?<=[\.])");

      foreach (string p in parts)
      {
        // Ignore empty strings added by the regex splitter
        if (p.Length == 0)
          continue;

        // Ignore last message if incomplete
        if (p[p.Length - 1] != '.')
          break;

        // process p
        Console.WriteLine("message received");
        Console.WriteLine(p);

        sb.Remove(0, p.Length);

      }
    }

    /// <summary>
    /// Enter an infinite loop that asks the user for a message to send
    /// </summary>
    /// <param name="s">The socket connected to the server</param>
    private void SendMessages(Socket s)
    {
      while (true)
      {
        string message = Console.ReadLine();
        byte[] messageBytes = Encoding.UTF8.GetBytes(message);
        // Begin sending the message
        s.BeginSend(messageBytes, 0, messageBytes.Length, SocketFlags.None, SendCallback, s);
      }
    }

    /// <summary>
    /// Async callback for when a send operation completes
    /// </summary>
    /// <param name="ar"></param>
    private void SendCallback(IAsyncResult ar)
    {
      Socket s = (Socket)ar.AsyncState;
      // Nothing much to do here, just conclude the send operation so the socket is happy.
      s.EndSend(ar);
    }
  }
}
