# Skywalking配置

```properties
-javaagent:C:\skywalking-java\skywalking-agent\skywalking-agent.jar
-Dskywalking.agent.keep_tracing=true
-Dskywalking.agent.service_name=dubbo-service-auth
-Dskywalking.collector.backend_service=localhost:11800
```
# Remote Debug配置监听

```properties
-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=6666
```