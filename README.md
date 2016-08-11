# Apache Camel IBM MQ component

Try to set a component to deal with IBM MQ in camel

## How to use it

Add the component to your camel context passing configuration parameters:  
``` 
context.addComponent("wmq", WmqComponent.newWmqComponent(hostname, port, queueManager, channel);
```

### As producer

Then you can send messages to a queue passing custom IBM properties as header:
```
from("direct:sendMessage")
    .setHeader("JMS_IBM_MQMD_ApplIdentityData", "your_custom_appiddata")
    .to("wmq:<queue_name>?username=<username>&password=<password>")
```

By default, the component sets default values for some IBM properties:
 - JMS_IBM_MQMD_PutDate
 - JMS_IBM_MQMD_PutTime
 - JMS_IBM_MQMD_PutApplName
 - JMS_IBM_Character_Set
 - JMS_IBM_Encoding
 - JMS_IBM_MsgType
 - JMS_IBM_PutApplType
 - JMS_IBM_PutApplType
 - JMS_IBM_MQMD_ApplIdentityData
 
You can see the values in the WMQProducer's code

### As consumer

It binds IBM properties by default on message headers

```
from("wmq:<queue_name>?username=<username>&password=<password>")
    .to(<your_endpoint>)
```
