using CallbackService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using WcfClient.ServiceReference1;
using IService1 = WcfService.IService1;
using WcfClient.ServiceReference2;
using Service1Client = WcfClient.ServiceReference1.Service1Client;

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
            Uri("http://localhost:8733/Design_Time_Addresses/WcfService/Service1/endpoint1");
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

            Console.Write("...calling Multiply (for endpoint2) - ");
            result = myClient2.Multiply(-3.7, 9.5); //just example values
            Console.WriteLine("Result = " + result);
            Console.WriteLine();

            result = asyResult.Result;
            Console.WriteLine("2...HMultiplyAsync Result = " + result);
            Console.WriteLine();

            SuperCalcCallback myCbHandler = new SuperCalcCallback();
            InstanceContext instanceContext = new InstanceContext(myCbHandler);
            SuperCalcClient myClient3 = new SuperCalcClient(instanceContext);
            double value1 = 10;
            Console.WriteLine("...calling Factorial({0})...", value1);
            myClient3.Factorial(value1);
            Console.WriteLine();

            Console.Write("...calling Multiply (for endpoint2) - ");
            result = myClient2.Multiply(-3.7, 9.5); //just example values
            Console.WriteLine("Result = " + result);
            Console.WriteLine();

            Console.ReadLine(); // to not finish app immediately:
                                // Step 3: Closing the client - closes connection and clears resources.
           
            ((IClientChannel)myClient).Close();
            Console.WriteLine("...Client closed - FINISHED");

            myClient3.Close();
            Console.WriteLine("CLIENT3 - STOP");
        }

        static async Task<double> callHMultiplyAsync(double n1, double n2, Service1Client myClient2)
        {
            Console.WriteLine("2......called callHMultiplyAsync");
            double reply = await myClient2.HMultiplyAsync(n1, n2);
            Console.WriteLine("2......finished HMultipleAsync");
            return reply;
        }

        class SuperCalcCallback : ServiceReference2.ISuperCalcCallback
        {
            public void FactorialResult(double result)
            {
                //here the result is consumed
                Console.WriteLine(" Factorial = {0}", result);
            }
        }


    }
}
