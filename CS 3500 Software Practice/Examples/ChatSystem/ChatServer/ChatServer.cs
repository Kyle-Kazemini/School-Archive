using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Text.RegularExpressions;


namespace ChatServer
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

  class ChatServer
  {
    private HashSet<Socket> clients;

    private TcpListener listener;

    static void Main(string[] args)
    {
      ChatServer server = new ChatServer();
      server.StartServer();

      Console.WriteLine("Server running...");

      // Hold the program open
      Console.Read();
    }

    /// <summary>
    /// Creates a new ChatServer object
    /// </summary>
    public ChatServer()
    {
      clients = new HashSet<Socket>();
    }

    /// <summary>
    /// Start accepting Tcp socket connections from clients
    /// </summary>
    public void StartServer()
    {
      // 1. initialize the listener
      listener = new TcpListener(IPAddress.Any, 11000);

      // 2. start the listener
      listener.Start();

      // 3. begin accepting a client (starts an event loop)
      listener.BeginAcceptSocket(OnClientConnect, null);
    }

    /// <summary>
    /// Callback for when a connection is made (see line 64)
    /// </summary>
    /// <param name="ar"></param>
    private void OnClientConnect(IAsyncResult ar)
    {
      Console.WriteLine("contact from client");

      Socket newClient = listener.EndAcceptSocket(ar);

      SocketState state = new SocketState(newClient);

      // Keep track of the client so we can broadcast to all of them
      lock (clients)
      {
        clients.Add(newClient);
      }

      // starts a receive loop
      state.theSocket.BeginReceive(state.messageBuffer, 0, state.messageBuffer.Length, SocketFlags.None,
        OnReceive, state);

      // continues an accept loop (started by line 64)
      listener.BeginAcceptSocket(OnClientConnect, null);
    }

    /// <summary>
    /// Callback for when data is received (see line 86)
    /// </summary>
    /// <param name="ar"></param>
    private void OnReceive(IAsyncResult ar)
    {
      SocketState state = (SocketState)ar.AsyncState;

      int numBytes = state.theSocket.EndReceive(ar);

      string data = Encoding.UTF8.GetString(state.messageBuffer, 0, numBytes);

      // Buffer the data received (we may not have a full message yet)
      state.sb.Append(data);

      // Process the data received so far
      ProcessMessages(state.sb);

      // continues a receive loop (started by line 86)
      state.theSocket.BeginReceive(state.messageBuffer, 0, state.messageBuffer.Length, SocketFlags.None,
        OnReceive, state);
    }


    /// <summary>
    /// Look for complete messages (terminated by a '.'), 
    /// then print and remove them from the string builder,
    /// and broadcast the message to all clients.
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

        Console.WriteLine("message received: " + p);

        // Broadcast the message by sending to all clients
        lock (clients)
        {
          foreach (Socket c in clients)
            Send(c, p);
        }
        sb.Remove(0, p.Length);
      }
    }

    /// <summary>
    /// Convenience wrapper around sending a string on a socket
    /// </summary>
    /// <param name="s"></param>
    /// <param name="message"></param>
    private void Send(Socket s, string message)
    {
      byte[] messageBytes = Encoding.UTF8.GetBytes(message);
      // Begin sending the message
      s.BeginSend(messageBytes, 0, messageBytes.Length, SocketFlags.None, SendCallback, s);
    }

    /// <summary>
    /// Async callback for when a send operation completes
    /// </summary>
    /// <param name="ar"></param>
    private void SendCallback(IAsyncResult ar)
    {
      // Nothing much to do here, just conclude the send operation so the socket is happy.
      Socket client = (Socket)ar.AsyncState;
      client.EndSend(ar);
    }

  }
}


