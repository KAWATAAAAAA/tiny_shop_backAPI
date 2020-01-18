>dao 层采用 mybatis 的 @Mapper 注解就省去了写 mapper.xml 映射文件，
使用 mybatis-plus 后，封装并提供了一套完整的简单查询，和条件包装器查询，

> 所以不再需要在dao 层中写sql 语句，例如：

```java
@Mapper
public interface UserDAO {
 
    @Select("select * from user where name = #{name}")
    public User find(String name);
 
    @Select("select * from user where name = #{name} and pwd = #{pwd}")
    /**
      * 对于多个参数来说，每个参数之前都要加上@Param注解，
      * 要不然会找不到对应的参数进而报错
      */
    public User login(@Param("name")String name, @Param("pwd")String pwd);

```

不过想要自定义程度高的sql语句还是得自己新建一个 mapper 包，在里面写 xml