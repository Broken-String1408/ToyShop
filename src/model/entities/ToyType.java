package model.entities;

public enum ToyType {
    PLASTIC_PARROT("пластиковый попугай", 5),
    TEDDY_BEAR("плюшевый медведь", 10),
    METAL_CAR("АВТОМОБИЛЬ", 15),
    FLAMINGO("розовый фламинго", 20),
    KANGAROO("кенгуру", 25),
    DOG("пёс", 30),
    UNICORN("единорог", 35);
    public final String label;
    public final int basePrice;
    ToyType(String label, int basePrice){
        this.label = label;
        this.basePrice = basePrice;
    }
}
