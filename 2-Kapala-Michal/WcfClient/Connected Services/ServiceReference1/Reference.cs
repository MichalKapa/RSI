﻿//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.42000
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace WcfClient.ServiceReference1 {
    
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ServiceModel.ServiceContractAttribute(ConfigurationName="ServiceReference1.IService1")]
    public interface IService1 {
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IService1/Add", ReplyAction="http://tempuri.org/IService1/AddResponse")]
        double Add(double val1, double val2);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IService1/Add", ReplyAction="http://tempuri.org/IService1/AddResponse")]
        System.Threading.Tasks.Task<double> AddAsync(double val1, double val2);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IService1/Multiply", ReplyAction="http://tempuri.org/IService1/MultiplyResponse")]
        double Multiply(double val1, double val2);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IService1/Multiply", ReplyAction="http://tempuri.org/IService1/MultiplyResponse")]
        System.Threading.Tasks.Task<double> MultiplyAsync(double val1, double val2);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IService1/HMultiply", ReplyAction="http://tempuri.org/IService1/HMultiplyResponse")]
        double HMultiply(double val1, double val2);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IService1/HMultiply", ReplyAction="http://tempuri.org/IService1/HMultiplyResponse")]
        System.Threading.Tasks.Task<double> HMultiplyAsync(double val1, double val2);
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    public interface IService1Channel : WcfClient.ServiceReference1.IService1, System.ServiceModel.IClientChannel {
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    public partial class Service1Client : System.ServiceModel.ClientBase<WcfClient.ServiceReference1.IService1>, WcfClient.ServiceReference1.IService1 {
        
        public Service1Client() {
        }
        
        public Service1Client(string endpointConfigurationName) : 
                base(endpointConfigurationName) {
        }
        
        public Service1Client(string endpointConfigurationName, string remoteAddress) : 
                base(endpointConfigurationName, remoteAddress) {
        }
        
        public Service1Client(string endpointConfigurationName, System.ServiceModel.EndpointAddress remoteAddress) : 
                base(endpointConfigurationName, remoteAddress) {
        }
        
        public Service1Client(System.ServiceModel.Channels.Binding binding, System.ServiceModel.EndpointAddress remoteAddress) : 
                base(binding, remoteAddress) {
        }
        
        public double Add(double val1, double val2) {
            return base.Channel.Add(val1, val2);
        }
        
        public System.Threading.Tasks.Task<double> AddAsync(double val1, double val2) {
            return base.Channel.AddAsync(val1, val2);
        }
        
        public double Multiply(double val1, double val2) {
            return base.Channel.Multiply(val1, val2);
        }
        
        public System.Threading.Tasks.Task<double> MultiplyAsync(double val1, double val2) {
            return base.Channel.MultiplyAsync(val1, val2);
        }
        
        public double HMultiply(double val1, double val2) {
            return base.Channel.HMultiply(val1, val2);
        }
        
        public System.Threading.Tasks.Task<double> HMultiplyAsync(double val1, double val2) {
            return base.Channel.HMultiplyAsync(val1, val2);
        }
    }
}
