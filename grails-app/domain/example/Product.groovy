package example

class Product {
    String color

    static void removeAllByColor(String givenColor) {
        where { color == givenColor }.deleteAll()
    }

}
