using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.Threading;

namespace WcfServiceLibrary
{
    [ServiceBehavior(InstanceContextMode = InstanceContextMode.Single, ConcurrencyMode = ConcurrencyMode.Multiple)]
    public class MyCalculator : ICalculator
    {
        public double Add(double val1, double val2)
        {
            double result = val1 + val2;
            Console.WriteLine("Add(" + val1 + ", " + val2 + ") -> " + result);
            return result;
        }

        public double HMultiply(double val1, double val2)
        {
            Thread.Sleep(5000);
            double result = val1 * val2;
            Console.WriteLine("HMultiply(" + val1 + ", " + val2 + ") -> " + result);
            return result;
        }

        public double Multiply(double val1, double val2)
        {
            double result = val1 * val2;
            Console.WriteLine("Multiply(" + val1 + ", " + val2 + ") -> " + result);
            return result;
        }
    }
}
