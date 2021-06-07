基于scala的springboot项目启动
测试项目

- idea需要安装scala插件
- 依次创建实体类entity
- dao/Repository，JpaRepository[]
- Service
- Controller

#### 类的例子
```java
@Service
class CustomerService @Resource()(@Autowired private val userRepository: UserRepository) {
  /**
   * 保存用户
   *
   * @param name 用户名
   */
  def saveCustomer(name: String): Unit = {
    var customer = customerRepository.findByCustomerName(name)
    if (customer != null) {
      throw MyServiceException("添加失败，用户已存在")
    }
    customer = new Customer(name)
    customerRepository.save(customer)
  }

  /**
   * 分页查询
   *
   * @param pageable 分页对象
   * @return
   */
  def pageCustomer(pageable: Pageable): PageResult[java.util.List[Customer]] = {
    val page = customerRepository.findAll(pageable)
    return PageResult.of(page.getContent, page.getTotalPages, page.getSize, page.getTotalElements, page.getNumber)
  }

  /**
   * 更新用户名
   * @param id 用户id
   * @param name 用户名
   */
  def updateCustomer(id: Integer, name: String): Unit = {
    val customer = customerRepository.findById(id).orElseThrow(() => MyServiceException("更新失败，用户不存在"))
    customer.setCustomerName(name)
    customerRepository.saveAndFlush(customer)
  }

  /**
   * 删除指定用户
   * @param id 用户id
   */
  def deleteCustomer(id:Integer): Unit = {
    val customer = customerRepository.findById(id).orElseThrow(() => MyServiceException("删除失败，用户不存在"))
    customerRepository.delete(customer)
  }
}
```

#### 接口的例子
```scala
@Repository
trait CustomerRepository extends JpaRepository[Customer, Integer] {
  /**
   * 通过用户名查询
   * @param name 用户名
   * @return Customer
   */
  def findByCustomerName(name:String) : Customer
}
```