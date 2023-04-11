using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using WcfClient.ServiceReference1;
using WcfService;
using IService1 = WcfService.IService1;

namespace WcfClient
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("... The client is started");
            // Step 1: Create client proxy based on communication channel.
            // base address:
            Uri baseAddress;
            // binding, address, endpoint address:
            BasicHttpBinding myBinding = new BasicHttpBinding();
            baseAddress = new
            Uri("http://localhost:10007/Service1/endpoint1");
            EndpointAddress eAddress = new EndpointAddress(baseAddress);
            // channel factory:
            ChannelFactory<IService1> myCF = new
            ChannelFactory<IService1>(myBinding, eAddress);
            // client proxy (here myClient) based on channel
            IService1 myClient = myCF.CreateChannel();
            // Step 2: service operations call.
            Console.Write("...calling Add (for entpoint1) ");
            double result = myClient.Add(-3.7, 9.5); //just example values
            Console.WriteLine("Result = " + result);
            // here possible other operations
            Console.WriteLine("...press <ENTER> to STOP client...");
            Console.WriteLine();

            Service1Client myClient2 = new Service1Client("WSHttpBinding_IService1");
            Console.Write("...calling Multiply (for endpoint2) - ");
            result = myClient2.Multiply(-3.7, 9.5); //just example values
            Console.WriteLine("Result = " + result);
            Console.WriteLine();

            Console.WriteLine("2...calling HMultiply ASYNCHRONOUSLY !!!");
            Task<double> asyResult = callHMultiplyAsync(1.1, -3.3, myClient2);
            Thread.Sleep(100);
            result = asyResult.Result;
            Console.WriteLine("2...HMultiplyAsync Result = " + result);
            Console.WriteLine();
            Console.ReadLine(); // to not finish app immediately:
                                // Step 3: Closing the client - closes connection and clears resources.
           
            ((IClientChannel)myClient).Close();
            Console.WriteLine("...Client closed - FINISHED");
        }

        static async Task<double> callHMultiplyAsync(double n1, double n2, Service1Client myClient2)
        {
            Console.WriteLine("2......called callHMultiplyAsync");
            double reply = await myClient2.HMultiplyAsync(n1, n2);
            Console.WriteLine("2......finished HMultipleAsync");
            return reply;
        }

    }
}
