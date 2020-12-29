using System;
using System.Threading;
using System.Diagnostics;

// Demonstration of two threads accessing shared memory

public class ThreadPractice
{

  public static void Main()
  {
    // Launch the demo
    ThreadPractice t1 = new ThreadPractice();
    t1.demo();
  }

  // The threads modify this variable
  private int count = 0;

  // Runs the demo
  public void demo()
  {

    // Fire off two threads
    Thread thread1 = new Thread(modify);
    Thread thread2 = new Thread(modify);

    thread1.Start();
    thread2.Start();

    Stopwatch watch = new Stopwatch();
    watch.Start();

    // As long as one of the threads is running, give
    // updates on the value of count
    while (thread1.IsAlive || thread2.IsAlive)
    {
      Console.WriteLine("count = " + count);
      Thread.Sleep(1000); // Don't spew too much to the console
    }

    // Note: We could Join the threads here, but the above loops waits for them in a different way.

    watch.Stop();
    Console.WriteLine("Took: " + watch.ElapsedMilliseconds + " milliseconds");

    // Display the final value of count.  If the threads are well-behaved,
    // it will be zero.
    Console.WriteLine("Final value of count = " + count);
    Console.Read();

  }


  // Runs a long loop that increments and then decrements the count.
  public void modify()
  {
    for (int i = 0; i < 1000000000; i++)
    {
      count++;
      count--;
    }
  }
}
