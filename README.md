# mybatistest
1.  mybatis学习

<mappers>
  <mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
  <mapper resource="org/mybatis/builder/BlogMapper.xml"/>
  <mapper resource="org/mybatis/builder/PostMapper.xml"/>
</mappers>
<!-- Using url fully qualified paths -->
<mappers>
  <mapper url="file:///var/mappers/AuthorMapper.xml"/>
  <mapper url="file:///var/mappers/BlogMapper.xml"/>
  <mapper url="file:///var/mappers/PostMapper.xml"/>
</mappers>
<!-- Using mapper interface classes -->
<mappers>
  <mapper class="org.mybatis.builder.AuthorMapper"/>
  <mapper class="org.mybatis.builder.BlogMapper"/>
  <mapper class="org.mybatis.builder.PostMapper"/>
</mappers>
<!-- Register all interfaces in a package as mappers -->
<mappers>
  <package name="org.mybatis.builder"/>
</mappers>
这些配置会告诉了 MyBatis 去哪里找映射文件。





2、配置文件里配置项目是有先后顺序的，依次是：properties,settings,typeAliases,typeHandlers,objectFactory,objectWrapperFactory,plugins,environments,databaseIdProvider,mappers，那个选项不配置，它的位置空出来，但顺序必须遵循，否则会报错。

　　3、properties选项，可导入外部以properties结尾的配置文件。通常会将数据库的连接信息放在该配置文件里，这时在配置dataSource时，value=“${driver}”，这种形式，此处driver对应的就是配置文件中driver = com.mysql.jdbc.Driver

　　4、在配置mapper时，有几种方式，分别是resource、url、class、package

　　resource形式：

　　<mappers>  
        <mapper resource="com/tiantian/mybatis/model/BlogMapper.xml"/>  
    </mappers>

　　这个路径是你xml映射文件的路径包名+文件名。

　　url形式：

　　<mappers>

　　　　<mapper url="file:///var/mappers/BlogMapper.xml"/> 

　　</mappers>

　　这个路径对应的是网络上了某个文件，注意file:// 前缀 +路径+文件名

　　class形式：

　　<mappers>

　　　　 <mapper class="org.mybatis.builder.BlogMapper"/> 

　　 </mappers>

　　这个class实际上是接口，写的是接口的全名。

　　package形式：

　　<mappers>

　　　　 <package name="org.mybatis.builder"/>

　　 </mappers>

　　5、实际项目中采用最多的是面向接口编程，也就是一个接口对应一个XML映射文件，如果让接口和映射文件对应呢？答案是XML映射文件中的mapper namespace="com.mybatis.inter.IUserOperation"，这个namespace中一定是接口的全名，不能是别名、简名，否则运行时会报错。一般我们会把接口名和映射文件名写成一样的，而且在同一个包下，所以感觉namespace就是xml文件的全名。

# 1.properties 属性

```
  <!--properties 引入外部配置文件 properties 的内容
    resource: 引入类路径资源
         url: 引入网络资源
  -->
  <properties resource="db.properties"/>
```

![](F:\shangchaun\196558-20180822163140710-418871755.png)

# setting 设置

这是 MyBatis 中极为重要的调整设置，它们会改变 MyBatis 的运行时行为。下表描述了设置中各项的意图、默认值等。

| 设置参数                         | 描述                                                         | 有效值                                                       | 默认值                                                |
| -------------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ | :---------------------------------------------------- |
| cacheEnabled                     | 全局地开启或关闭配置文件中的所有映射器已经配置的任何缓存。   | true \| false                                                | true                                                  |
| lazyLoadingEnabled               | 延迟加载的全局开关。当开启时，所有关联对象都会延迟加载。 特定关联关系中可通过设置 `fetchType`属性来覆盖该项的开关状态。 | true \| false                                                | false                                                 |
| aggressiveLazyLoading            | 当开启时，任何方法的调用都会加载该对象的所有属性。否则，每个属性会按需加载（参考 `lazyLoadTriggerMethods`). | true \| false                                                | false (true in ≤3.4.1)                                |
| multipleResultSetsEnabled        | 是否允许单一语句返回多结果集（需要兼容驱动）。               | true \| false                                                | true                                                  |
| useColumnLabel                   | 使用列标签代替列名。不同的驱动在这方面会有不同的表现， 具体可参考相关驱动文档或通过测试这两种不同的模式来观察所用驱动的结果。 | true \| false                                                | true                                                  |
| useGeneratedKeys                 | 允许 JDBC 支持自动生成主键，需要驱动兼容。 如果设置为 true 则这个设置强制使用自动生成主键，尽管一些驱动不能兼容但仍可正常工作（比如 Derby）。 | true \| false                                                | False                                                 |
| autoMappingBehavior              | 指定 MyBatis 应如何自动映射列到字段或属性。 NONE 表示取消自动映射；PARTIAL 只会自动映射没有定义嵌套结果集映射的结果集。 FULL 会自动映射任意复杂的结果集（无论是否嵌套）。 | NONE, PARTIAL, FULL                                          | PARTIAL                                               |
| autoMappingUnknownColumnBehavior | 指定发现自动映射目标未知列（或者未知属性类型）的行为。`NONE`: 不做任何反应`WARNING`: 输出提醒日志 (`'org.apache.ibatis.session.AutoMappingUnknownColumnBehavior'` 的日志等级必须设置为 `WARN`)`FAILING`: 映射失败 (抛出 `SqlSessionException`) | NONE, WARNING, FAILING                                       | NONE                                                  |
| defaultExecutorType              | 配置默认的执行器。SIMPLE 就是普通的执行器；REUSE 执行器会重用预处理语句（prepared statements）； BATCH 执行器将重用语句并执行批量更新。 | SIMPLE REUSE BATCH                                           | SIMPLE                                                |
| defaultStatementTimeout          | 设置超时时间，它决定驱动等待数据库响应的秒数。               | 任意正整数                                                   | Not Set (null)                                        |
| defaultFetchSize                 | 为驱动的结果集获取数量（fetchSize）设置一个提示值。此参数只可以在查询设置中被覆盖。 | 任意正整数                                                   | Not Set (null)                                        |
| safeRowBoundsEnabled             | 允许在嵌套语句中使用分页（RowBounds）。如果允许使用则设置为false。 | true \| false                                                | False                                                 |
| safeResultHandlerEnabled         | 允许在嵌套语句中使用分页（ResultHandler）。如果允许使用则设置为false。 | true \| false                                                | True                                                  |
| mapUnderscoreToCamelCase         | 是否开启自动驼峰命名规则（camel case）映射，即从经典数据库列名 A_COLUMN 到经典 Java 属性名 aColumn 的类似映射。 | true \| false                                                | False                                                 |
| localCacheScope                  | MyBatis 利用本地缓存机制（Local Cache）防止循环引用（circular references）和加速重复嵌套查询。 默认值为 SESSION，这种情况下会缓存一个会话中执行的所有查询。 若设置值为 STATEMENT，本地会话仅用在语句执行上，对相同 SqlSession 的不同调用将不会共享数据。 | SESSION \| STATEMENT                                         | SESSION                                               |
| jdbcTypeForNull                  | 当没有为参数提供特定的 JDBC 类型时，为空值指定 JDBC 类型。 某些驱动需要指定列的 JDBC 类型，多数情况直接用一般类型即可，比如 NULL、VARCHAR 或 OTHER。 | JdbcType 常量. 大多都为: NULL, VARCHAR and OTHER             | OTHER                                                 |
| lazyLoadTriggerMethods           | 指定哪个对象的方法触发一次延迟加载。                         | 用逗号分隔的方法列表。                                       | equals,clone,hashCode,toString                        |
| defaultScriptingLanguage         | 指定动态 SQL 生成的默认语言。                                | 一个类型别名或完全限定类名。                                 | org.apache.ibatis.scripting.xmltags.XMLLanguageDriver |
| defaultEnumTypeHandler           | 指定 Enum 使用的默认 `TypeHandler` 。 (从3.4.5开始)          | 一个类型别名或完全限定类名。                                 | org.apache.ibatis.type.EnumTypeHandler                |
| callSettersOnNulls               | 指定当结果集中值为 null 的时候是否调用映射对象的 setter（map 对象时为 put）方法，这对于有 Map.keySet() 依赖或 null 值初始化的时候是有用的。注意基本类型（int、boolean等）是不能设置成 null 的。 | true \| false                                                | false                                                 |
| returnInstanceForEmptyRow        | 当返回行的所有列都是空时，MyBatis默认返回 `null`。 当开启这个设置时，MyBatis会返回一个空实例。 请注意，它也适用于嵌套的结果集 (i.e. collectioin and association)。（从3.4.2开始） | true \| false                                                | false                                                 |
| logPrefix                        | 指定 MyBatis 增加到日志名称的前缀。                          | 任何字符串                                                   | Not set                                               |
| logImpl                          | 指定 MyBatis 所用日志的具体实现，未指定时将自动查找。        | SLF4J \| LOG4J \| LOG4J2 \| JDK_LOGGING \| COMMONS_LOGGING \| STDOUT_LOGGING \| NO_LOGGING | Not set                                               |
| proxyFactory                     | 指定 Mybatis 创建具有延迟加载能力的对象所用到的代理工具。    | CGLIB \| JAVASSIST                                           | JAVASSIST (MyBatis 3.3 or above)                      |
| vfsImpl                          | 指定VFS的实现                                                | 自定义VFS的实现的类全限定名，以逗号分隔。                    | Not set                                               |
| useActualParamName               | 允许使用方法签名中的名称作为语句参数名称。 为了使用该特性，你的工程必须采用Java 8编译，并且加上 `-parameters`选项。（从3.4.1开始） | true \| false                                                | true                                                  |
| configurationFactory             | 指定一个提供 `Configuration`实例的类。 这个被返回的Configuration实例用来加载被反序列化对象的懒加载属性值。 这个类必须包含一个签名方法 `static Configuration getConfiguration()`. (从 3.2.3 版本开始) | 类型别名或者全类名.                                          | Not set                                               |

一个配置完整的 settings 元素的示例如下：

```
<settings>
  <setting name="cacheEnabled" value="true"/>
  <setting name="lazyLoadingEnabled" value="true"/>
  <setting name="multipleResultSetsEnabled" value="true"/>
  <setting name="useColumnLabel" value="true"/>
  <setting name="useGeneratedKeys" value="false"/>
  <setting name="autoMappingBehavior" value="PARTIAL"/>
  <setting name="autoMappingUnknownColumnBehavior" value="WARNING"/>
  <setting name="defaultExecutorType" value="SIMPLE"/>
  <setting name="defaultStatementTimeout" value="25"/>
  <setting name="defaultFetchSize" value="100"/>
  <setting name="safeRowBoundsEnabled" value="false"/>
  <setting name="mapUnderscoreToCamelCase" value="false"/>
  <setting name="localCacheScope" value="SESSION"/>
  <setting name="jdbcTypeForNull" value="OTHER"/>
  <setting name="lazyLoadTriggerMethods" value="equals,clone,hashCode,toString"/>
</settings>
```

**例子：**
在前面入门里 <https://www.cnblogs.com/tangge/p/9517376.html#t7>

```
  <select id="getEmployeeById" resultType="com.tangge.model.employee">
    select `id`,  `last_name`  lastName,  `gender`,  `email` from tbl_employee where id = #{id}
  </select>
```

把 SQL 改为 `select * from tbl_employee where id = #{id}`
这时候 lastName 读取不了数据，因为数据字段名为：last_name
如果我们启动驼峰命名，即从经典数据库列名 A_COLUMN 到经典 Java 属性名 aColumn 的类似映射。
**这里为 last_name到经典属性名 lastName的映射**。

```
 <settings>
    <!--是否开启自动驼峰命名规则（camel case）映射，-->
    <setting name="mapUnderscoreToCamelCase" value="true"/>
  </settings>
```

# 3.typeAliases 类型别名

类型别名是为 Java 类型设置一个短的名字。它只和 XML 配置有关，存在的意义仅在于用来减少类完全限定名的冗余。例如:

```
<!--typeAliases : 别名处理器，为JAVA类型起别名，别名不区分大小写 -->
  <typeAliases>
    <!--
    typeAlias：为某个java类型取别名
          type:指定要起别名的类型全类名；默认别名就是类名小写；
          alias:指定新的别名
                -->
    <typeAlias type="com.tangge.model.employee" alias="employee"/>-->
  </typeAliases>
```

也可以指定一个包名，MyBatis 会在包名下面搜索需要的 Java Bean，比如:

```
  <typeAliases>
    <!--
    package  批量起别名：
      name：指定包名(为当前包以及下面所有后代包的每一个类都起一个默认别名(类名小写)
      可以使用@Alias("emp")注解，为某个类型起新别名
    -->
    <package name="com.tangge.model"/>
  </typeAliases>
```

若有注解，则别名为其注解值。

```
@Alias("emp")
public class employee {
...
}
```

这是一些为常见的 Java 类型内建的相应的类型别名。它们都是大小写不敏感的，需要注意的是由基本类型名称重复导致的特殊处理。

别名 映射的类型

```
_byte byte
_long long
_short short
_int int
_integer int
_double double
_float float
_boolean boolean
string String
byte Byte
long Long
short Short
int Integer
integer Integer
double Double
float Float
boolean Boolean
date Date
decimal BigDecimal
bigdecimal BigDecimal
object Object
map Map
hashmap HashMap
list List
arraylist ArrayList
collection Collection
iterator Iterator
```

# 4.typeHandlers 类型处理

无论是 MyBatis 在预处理语句（PreparedStatement）中设置一个参数时，还是从结果集中取出一个值时， 都会用类型处理器将获取的值以合适的方式转换成 Java 类型。下表描述了一些默认的类型处理器。

`提示` 从 3.4.5 开始，MyBatis 默认支持 JSR-310(日期和时间 API) 。

[typeHandlers介绍](http://www.mybatis.org/mybatis-3/zh/configuration.html#typeHandlers)

# 5.plugins 插件

MyBatis 允许你在已映射语句执行过程中的某一点进行拦截调用。默认情况下，MyBatis 允许使用插件来拦截的方法调用包括：

Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
ParameterHandler (getParameterObject, setParameters)
ResultSetHandler (handleResultSets, handleOutputParameters)
StatementHandler (prepare, parameterize, batch, update, query)

# 6.environments 环境

MyBatis 可以配置成适应多种环境，这种机制有助于将 SQL 映射应用于多种数据库之中， 现实情况下有多种理由需要这么做。例如，开发、测试和生产环境需要有不同的配置；或者共享相同 Schema 的多个生产数据库， 想使用相同的 SQL 映射。许多类似的用例。

```
<!--
  环境 environments： 可以配置多种环境，default 指定运行哪个环境。快速切换
       - environment： 配置一个具体环境
          * transactionManager： 事务管理器.
            - type：[JDBC|MANAGED|自定义]
              * JDBC：（JdbcTransactionFactory）
              * MANAGED：（ManagedTransactionFactory）
              * 自定义：实现（TransactionFactory）
          * dataSource：数据源
            - type：[UNPOOLED|POOLED|JNDI]
              * UNPOOLED：（UnpooledDataSourceFactory）
              * POOLED：（PooledDataSourceFactory）
              * JNDI：（JndiDataSourceFactory）
              * 自定义：实现（DataSourceFactory）
  -->
  <environments default="development">
    <!--测试环境-->
    <environment id="test">
      <transactionManager type=""></transactionManager>
      <dataSource type=""></dataSource>
    </environment>
    <!--开发环境-->
    <environment id="development">
      <transactionManager type="JDBC"></transactionManager>
      <dataSource type="POOLED">
        <property name="driver" value="${drivername}"/>
        <property name="url" value="${url}"/>
        <property name="username" value="${user}"/>
        <property name="password" value="${pass}"/>
      </dataSource>
    </environment>
  </environments>
```

**事务管理器（transactionManager）** 在 MyBatis 中有两种类型的事务管理器（也就是 `type=”[JDBC|MANAGED]”）`： 

- JDBC：使用了 JDBC 的提交和回滚设置，依赖于从数据源得到的连接来管理事务范围。(JdbcTransactionFactory)

- MANAGED：不提交或回滚一个连接、让容器来管理事务的整个生命周期（比如 JEE 应用服务器的上下文）。 (ManagedTransactionFactory)

- 自定义：实现TransactionFactory接口，type=全类名/别名

  

**数据源（dataSource）** 

* `type： UNPOOLED | POOLED | JNDI |` 自定义 

- ​    UNPOOLED：不使用连接池，UnpooledDataSourceFactory
- POOLED：使用连接池， PooledDataSourceFactory
- JNDI： 在EJB 或应用服务器这类容器中查找指定的数据源
- 自定义：实现DataSourceFactory接口，定义数据源的获取方式。
  - 实际开发中我们使用Spring管理数据源，并进行事务控制的配置来覆盖上述配置

# 7.databaseIdProvider 多数据库提供商

MyBatis 可以根据不同的数据库厂商执行不同的语句，这种多厂商的支持是基于映射语句中的 `databaseId` 属性。
MyBatis 会加载不带 databaseId 属性和带有匹配当前数据库 databaseId 属性的所有语句。 如果同时找到带有 databaseId 和不带 databaseId 的相同语句，则后者会被舍弃。

```
<!--databaseIdProvider:多数据库厂商-->
  <databaseIdProvider type="DB_VENDOR">
```

这里的 `DB_VENDOR` 会通过 `DatabaseMetaData#getDatabaseProductName()` 返回的字符串进行设置。 由于通常情况下这个字符串都非常长而且相同产品的不同版本会返回不同的值，所以最好通过设置属性别名来使其变短

```
<databaseIdProvider type="DB_VENDOR"> <property name="SQL Server" value="sqlserver"/> <property name="MySQL" value="mysql"/> <property name="DB2" value="db2"/> <property name="Oracle" value="oracle" /> </databaseIdProvider> 
```

- Type： DB_VENDOR
  使用MyBatis提供的VendorDatabaseIdProvider解析数据库厂商标识。也可以实现DatabaseIdProvider接口来自定义。
- Property-name：数据库厂商标识
- Property-value：为标识起一个别名，方便SQL语句使用databaseId属性引用

在Mapper.xml，看`databaseId`

```
  <select id="selectEmployee" resultType="com.tangge.model.employee" databaseId="mysql">
    select `id`,  `last_name`  lastName,  `gender`,  `email` from tbl_employee where id = #{id}
  </select>

  <select id="selectEmployee" resultType="com.tangge.model.employee" databaseId="oracle">
    select * from Employee where id = #{id}
  </select>
```

在提供了属性别名时，DB_VENDOR databaseIdProvider 将被设置为第一个能匹配数据库产品名称的属性键对应的值，如果没有匹配的属性将会设置为 “null”。 在这个例子中，如果 `getDatabaseProductName()` 返回“Oracle (DataDirect)”，databaseId 将被设置为“oracle”。

你可以通过实现接口 `org.apache.ibatis.mapping.DatabaseIdProvider` 并在 mybatis-config.xml 中注册来构建自己的 DatabaseIdProvider：

```
public interface DatabaseIdProvider {
  void setProperties(Properties p);
  String getDatabaseId(DataSource dataSource) throws SQLException;
}
```

# 8. mappers（映射器）

既然 MyBatis 的行为已经由上述元素配置完了，我们现在就要定义 SQL 映射语句了。但是首先我们需要告诉 MyBatis 到哪里去找到这些语句。 Java 在自动查找这方面没有提供一个很好的方法，所以最佳的方式是告诉 MyBatis 到哪里去找映射文件。你可以使用相对于类路径的资源引用， 或完全限定资源定位符（包括 file:/// 的 URL），或类名和包名等。

```
 <!--SQL映射文件（EmplyoeeMapper.xml）注册到全局配置文件（mybatis-config.xml）中
  * mapper:
    - resource:
    使用相对于类路径的资源引用
      示例：<mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
    - url:
    使用完全限定资源定位符（URL）
      示例：<mapper url="file:///var/mappers/AuthorMapper.xml"/>
    - class:
    使用映射器接口实现类的完全限定类名
    缺点：
    1.有SQL映射文件，映射文件与接口同名，并且与接口同一目录下
    2.没有SQL映射文件，SQL写在注解上。（快速开发用，一般不使用）
      示例：<mapper class="org.mybatis.builder.AuthorMapper"/>
  * package:
  缺点：有SQL映射文件，映射文件与接口同名，并且与接口同一目录下
  将包内的映射器接口实现全部注册为映射器
    - name:
      示例：<package name="org.mybatis.builder"/>
  -->
  <mappers>
    <mapper resource="EmplyoeeMapper.xml" />
  </mappers>
```

![](F:\shangchaun\196558-20180823105459689-164536756.png)

```
<!-- 使用相对于类路径的资源引用 -->
<mappers>
  <mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
  <mapper resource="org/mybatis/builder/BlogMapper.xml"/>
  <mapper resource="org/mybatis/builder/PostMapper.xml"/>
</mappers>
```

```
<!-- 使用完全限定资源定位符（URL） -->
<mappers>
  <mapper url="file:///var/mappers/AuthorMapper.xml"/>
  <mapper url="file:///var/mappers/BlogMapper.xml"/>
  <mapper url="file:///var/mappers/PostMapper.xml"/>
</mappers>
<!-- 使用映射器接口实现类的完全限定类名 -->
<mappers>
  <mapper class="org.mybatis.builder.AuthorMapper"/>
  <mapper class="org.mybatis.builder.BlogMapper"/>
  <mapper class="org.mybatis.builder.PostMapper"/>
</mappers>
<!-- 将包内的映射器接口实现全部注册为映射器 -->
<mappers>
  <package name="org.mybatis.builder"/>
</mappers>
```