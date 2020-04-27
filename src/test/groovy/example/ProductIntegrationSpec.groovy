package example

import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import spock.lang.Specification

@Integration
class ProductIntegrationSpec extends Specification {

    @Rollback
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
