using CallbackService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.ServiceModel.Description;
using System.Text;
using System.Threading.Tasks;
using WcfService;

namespace WcfServiceHost
{
    class Program
    {
        static void Main(string[] args)
        {
            // Krok 1 URI dla bazowego adresu serwisu
            Uri baseAddress = new Uri("http://localhost:10007/Service1");
            // Krok 2 Instancja serwisu
            ServiceHost myHost = new
            ServiceHost(typeof(Service1), baseAddress);
            // Krok 3 Endpoint serwisu
            BasicHttpBinding myBinding = new BasicHttpBinding();
            ServiceEndpoint endpoint1 = myHost.AddServiceEndpoint(
            typeof(IService1),
            myBinding,
            "endpoint1");
            // Krok 4 Ustawienie metadanych
            ServiceMetadataBehavior smb = new ServiceMetadataBehavior();
            smb.HttpGetEnabled = true;
            myHost.Description.Behaviors.Add(smb);
            WSHttpBinding binding2 = new WSHttpBinding();
            binding2.Security.Mode = SecurityMode.None;
            ServiceEndpoint endpoint2 = myHost.AddServiceEndpoint(
             typeof(IService1),
             binding2, "endpoint2");
            Uri baseAddress3 = new Uri("http://localhost:10007/SuperCalc");
            ServiceHost myHost3 = new
            ServiceHost(typeof(MySuperCalc), baseAddress3);
            WSDualHttpBinding myBinding3 = new WSDualHttpBinding();
            ServiceEndpoint endpoint3 =
            myHost3.AddServiceEndpoint(typeof(ISuperCalc),
            myBinding3, "endpoint3");
            myHost3.Description.Behaviors.Add(smb);
                try
            {
                // Krok 5 Uruchomienie serwisu.
                myHost.Open();
                Console.WriteLine("Service is started and running.");

                myHost3.Open();
                Console.WriteLine("--> Service SuperCalc is running.");

                Console.WriteLine("Press <ENTER> to STOP service...");
                Console.WriteLine();

                Console.WriteLine("\n---> Endpoints:");
                Console.WriteLine("\nService endpoint {0}:", endpoint1.Name);
                Console.WriteLine("Binding: {0}", endpoint1.Binding.ToString());
                Console.WriteLine("ListenUri: {0}", endpoint1.ListenUri.ToString());
                Console.WriteLine("\nService endpoint {0}:", endpoint2.Name);
                Console.WriteLine("Binding: {0}", endpoint2.Binding.ToString());
                Console.WriteLine("ListenUri: {0}", endpoint2.ListenUri.ToString());

                Console.ReadLine(); // aby nie kończyć natychmiast:
                myHost.Close();
                myHost3.Close();
            }
            catch (CommunicationException ce)
            {
                Console.WriteLine("Exception occured: {0}", ce.Message);
                myHost.Abort();
                myHost3.Abort();
            }
        }
    }
}
