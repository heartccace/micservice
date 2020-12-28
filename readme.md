### CAP

CAP原则又称CAP定理，指的是在一个分布式系统中，Consistency（一致性）、 Availability（可用性）、Partition tolerance（分区容错性），三者不可得兼

一致性（C）：在分布式系统中的所有数据备份，在同一时刻是否同样的值。（等同于所有节点访问同一份最新的数据副本）
可用性（A）：在集群中一部分节点故障后，集群整体是否还能响应客户端的读写请求。（对数据更新具备高可用性）
分区容错性（P）：以实际效果而言，分区相当于对通信的时限要求。系统如果不能在时限内达成数据一致性，就意味着发生了分区的情况，必须就当前操作在C和A之间做出选择。



ac：放弃分区容忍性，物理数据库

ap：可以短暂允许数据不一致，Nosql数据库

cp：放弃可用性



**在此ZooKeeper保证的是CP**

**分析：可用性（A:Available）**

**不能保证每次服务请求的可用性**。任何时刻对ZooKeeper的访问请求能得到一致的数据结果，同时系统对网络分割具备容错性；但是它不能保证每次服务请求的可用性（注：也就是在极端环境下，ZooKeeper可能会丢弃一些请求，消费者程序需要重新请求才能获得结果）。所以说，ZooKeeper不能保证服务可用性。

**进行leader选举时集群都是不可用**。在使用ZooKeeper获取服务列表时，当master节点因为网络故障与其他节点失去联系时，剩余节点会重新进行leader选举。问题在于，选举leader的时间太长，30 ~ 120s, 且选举期间整个zk集群都是不可用的，这就导致在选举期间注册服务瘫痪，虽然服务能够最终恢复，但是漫长的选举时间导致的注册长期不可用是不能容忍的。所以说，ZooKeeper不能保证服务可用性。



### Eurake

spring框架提供了RestTemplate可用于应用中调用rest服务，简化了http通信的方式。

通过RestTemplate调用微服务

```
//配置RestTemplate交给spring管理
@Bean
public RestTemplate getRestTemplate() {
return new RestTemplate();
}
```

```
@PostMapping("/{id}")
public String order(Integer num) {
//通过restTemplate调用商品微服务
Product object =
restTemplate.getForObject("http://127.0.0.1:9002/product/1", Product.class);
System.out.println(object);
return "操作成功";
}
```

服务注册中心框架：

```
组件名 语言 CAP 一致性算法 服务健康检查 对外暴露接口
Eureka Java AP 无 可配支持 HTTP
Consul Go CP Raft 支持 HTTP/DNS
Zookeeper Java CP Paxos 支持 客户端
Nacos Java AP Raft 支持 HTTP
```

Eureka Client是一个Java客户端，用于简化与Eureka Server的交互；
Eureka Server提供服务发现的能力，各个微服务启动时，会通过Eureka Client向Eureka Server进行注册自己的信息（例如网络信息），Eureka Server会存储该服务的信息；
微服务启动后，会周期性地向Eureka Server发送心跳（默认周期为30秒）以续约自己的信息。如果Eureka Server在一定时间内没有接收到某个微服务节点的心跳，Eureka Server将会注销该微服务节点（默认90秒）；
每个Eureka Server同时也是Eureka Client，多个Eureka Server之间通过复制的方式完成服务注册表的同步；
Eureka Client会缓存Eureka Server中的信息。即使所有的Eureka Server节点都宕掉，服务消费者依然可以使用缓存中的信息找到服务提供者。



```
registerWithEureka: 是否将自己注册到Eureka服务中，本身就是所有无需注册
fetchRegistry : 是否从Eureka中获取注册信息
serviceUrlEureka: 客户端与Eureka服务端进行交互的地址
```





```
@SpringBootApplication
//@EnableDiscoveryClient
//@EnableEurekaClient
public class UserApplication {
public static void main(String[] args) {
SpringApplication.run(UserApplication.class, args);
}
}

从Spring Cloud Edgware版本开始， @EnableDiscoveryClient 或 @EnableEurekaClient 可省略。只需加上相关依赖，并进行相应配置，即可将微服务注册到服务发现组件上。
```

#### Eureka中的自我保护

微服务第一次注册成功之后，每30秒会发送一次心跳将服务的实例信息注册到注册中心。通知 Eureka Server 该实例仍然存在。如果超过90秒没有发送更新，则服务器将从注册信息中将此服务移除。

Eureka Server在运行期间，会统计心跳失败的比例在15分钟之内是否低于85%，如果出现低于的情况（在单机调试的时候很容易满足，实际在生产环境上通常是由于网络不稳定导致），Eureka Server会将当前的实例注册信息保护起来，同时提示这个警告。保护模式主要用于一组客户端和Eureka Server
之间存在网络分区场景下的保护。一旦进入保护模式，Eureka Server将会尝试保护其服务注册表中的信息，不再删除服务注册表中的数据（也就是不会注销任何微服务）
验证完自我保护机制开启后，并不会马上呈现到web上，而是默认需等待 5 分钟（可以通过eureka.server.wait-time-in-ms-when-sync-empty 配置），即 5 分钟后你会看到eureka主页红色字体信息。

通过设置 eureka.enableSelfPreservation=false 来关闭自我保护功能

#### Eureka中的元数据

Eureka的元数据有两种：标准元数据和自定义元数据。
 标准元数据：主机名、IP地址、端口号、状态页和健康检查等信息，这些信息都会被发布在服务注册表中，用于服务之间的调用。
自定义元数据：可以使用eureka.instance.metadata-map配置，符合KEY/VALUE的存储格式。这些元数据可以在远程客户端中访问

#### Eureka Server 高可用集群

在上一个章节，实现了单节点的Eureka Server的服务注册与服务发现功能。Eureka Client会定时连接Eureka Server，获取注册表中的信息并缓存到本地。微服务在消费远程API时总是使用本地缓存中的数据。因此一般来说，即使Eureka Server发生宕机，也不会影响到服务之间的调用。但如果Eureka
Server宕机时，某些微服务也出现了不可用的情况，Eureka Server中的缓存若不被刷新，就可能会影响到微服务的调用，甚至影响到整个应用系统的高可用。因此，在生成环境中，通常会部署一个高可用的Eureka Server集群。
Eureka Server可以通过运行多个实例并相互注册的方式实现高可用部署，Eureka Server实例会彼此增量地同步信息，从而确保所有节点数据一致。事实上，节点之间相互注册是Eureka Server的默认行为。

#### Eureka中的常见问题

默认情况下，服务注册到Eureka Server的过程较慢。SpringCloud官方文档中给出了详细的原因：

大致含义：服务的注册涉及到心跳，默认心跳间隔为30s。在实例、服务器、客户端都在本地缓存中具有相同的元数据之前，服务不可用于客户端发现（所以可能需要3次心跳）。可以通过配置
eureka.instance.leaseRenewalIntervalInSeconds (心跳频率)加快客户端连接到其他服务的过程。在生产中，最好坚持使用默认值，因为在服务器内部有一些计算，他们对续约做出假设。

#####  服务节点剔除问题

默认情况下，由于Eureka Server剔除失效服务间隔时间为90s且存在自我保护的机制。所以不能有效而迅速的剔除失效节点，这对开发或测试会造成困扰。解决方案如下：
Eureka Server：
配置关闭自我保护，设置剔除无效节点的时间间隔

```
eureka:
instance:
 hostname: eureka1
client:
 service-url:
  defaultZone: http://eureka2:8762/eureka
server:
 enable-self-preservation: false  #关闭自我保护
 eviction-interval-timer-in-ms: 4000 #剔除时间间隔,单位:毫秒
```

Eureka Client：
配置开启健康检查，并设置续约时间

```
eureka:
client:
 healthcheck: true #开启健康检查(依赖spring-boot-actuator)
 serviceUrl:
  defaultZone: http://eureka1:8761/eureka/,http://eureka1:8761/eureka/
instance:
 preferIpAddress: true
 instance-id: ${spring.cloud.client.ip-address}:${server.port} #在控制台显示ip
 lease-expiration-duration-in-seconds: 10 #eureka client发送心跳给server端后，续
约到期时间（默认90秒）
 lease-renewal-interval-in-seconds: 5 #发送心跳续约间隔
```





### ribbon

负载均衡分为：

1. 服务器端负载均衡（通过nginx、F5）
2. 客户端负载均衡（客户端保留微服务地址，在本地实现负载均衡eureka的实现）

ribbon负载均衡实现：

- 轮询
- 随机
- 权重
- 请求最少



### Feign

Feign集成了Ribbon，自带负载均衡策略。

Feign数据压缩：

当请求或者接受的数据较大时，可以对数据进行压缩