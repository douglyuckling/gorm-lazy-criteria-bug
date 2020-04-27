package example

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class ProductUnitSpec extends Specification implements DomainUnitTest<Product> {

    void "can remove all products of a specified color"() {
        given:
        new Product(color: 'red').save(failOnError: true)
        new Product(color: 'orange').save(failOnError: true)
        new Product(color: 'yellow').save(failOnError: true)
        new Product(color: 'orange').save(failOnError: true)
        new Product(color: 'blue').save(failOnError: true)

        when:
        Product.removeAllByColor('orange')

        then:
        Product.findAll()*.color.toSet() == ['red', 'yellow', 'blue'].toSet()
    }

}
