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
            Uri baseAddress = new Uri("http://localhost:10000/Service1");
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
            try
            {
                // Krok 5 Uruchomienie serwisu.
                myHost.Open();
                Console.WriteLine("Service is started and running.");
                Console.WriteLine("Press <ENTER> to STOP service...");
                Console.WriteLine();
                Console.ReadLine(); // aby nie kończyć natychmiast:
                myHost.Close();
            }
            catch (CommunicationException ce)
            {
                Console.WriteLine("Exception occured: {0}", ce.Message);
                myHost.Abort();
            }
        }
    }
}
