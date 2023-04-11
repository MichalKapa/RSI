using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace WcfService
{
    // UWAGA: możesz użyć polecenia „Zmień nazwę” w menu „Refaktoryzuj”, aby zmienić nazwę klasy „Service1” w kodzie i pliku konfiguracji.

    public class Service1 : IService1
    {
        public double Add(double val1, double val2)
        {
            double result = val1 + val2;
            Console.WriteLine("Wykonuje operacje dodawania liczb: " + val1 + " i " + val2);
            Console.WriteLine("Wynik dodawania: " + result);
            return result;
 }
        public double Multiply(double val1, double val2)
        {
            double result = val1 * val2;
            Console.WriteLine("Wykonuje operacje mnożenia liczb: " + val1 + " i " + val2);
            Console.WriteLine("Wynik mnożenia: " + result);
            return result;
        }
    }
}
