package machine;

import java.util.Scanner;

public class CoffeeMachine {

    int water = 400;
    int milk = 540;
    int coffeeBeans = 120;
    int cups = 9;
    int money = 550;
    StateCoffeeMachine stateCoffeeMachine = StateCoffeeMachine.WAITING;
    boolean isContinue = true;

    public CoffeeMachine() {
        System.out.println("Write action (buy, fill, take, remaining, exit):");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        String action;

        do {
            action = scanner.nextLine();
            coffeeMachine.input(action);
        }while(coffeeMachine.stateCoffeeMachine != StateCoffeeMachine.DOWN);
    }

    public void input(String petition) {
        switch (stateCoffeeMachine) {
            case WAITING:
                switch (petition) {
                    case "buy":
                        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                        stateCoffeeMachine = StateCoffeeMachine.BUYING;
                        break;
                    case "fill":
                        System.out.println("Write how many ml of water do you want to add:");
                        stateCoffeeMachine = StateCoffeeMachine.FILLING_WATER;
                        break;
                    case "take":
                        System.out.println("I gave you $" + money);
                        money = 0;
                        break;
                    case "exit":
                        stateCoffeeMachine = StateCoffeeMachine.DOWN;
                        break;
                    case "remaining":
                        printCurrentQuantities();
                        System.out.println("Write action (buy, fill, take, remaining, exit):");
                        break;
                    default:
                        System.out.println("Unsupported action.");
                        break;
                }
                break;
            case FILLING_WATER:
                water += Integer.parseInt(petition);
                System.out.println("Write how many ml of milk do you want to add:");
                stateCoffeeMachine = StateCoffeeMachine.FILLING_MILK;
                break;

            case FILLING_MILK:
                milk += Integer.parseInt(petition);
                System.out.println("Write how many grams of coffee beans do you want to add:");
                stateCoffeeMachine = StateCoffeeMachine.FILLING_COFFEE;
                break;

            case FILLING_COFFEE:
                coffeeBeans += Integer.parseInt(petition);
                stateCoffeeMachine = StateCoffeeMachine.FILLING_CUPS;
                System.out.println("Write how many disposable cups of coffee do you want to add:");
                break;

            case FILLING_CUPS:
                cups += Integer.parseInt(petition);
                System.out.println("Write action (buy, fill, take, remaining, exit):");
                stateCoffeeMachine = StateCoffeeMachine.WAITING;
                break;
            case BUYING:
                buyCoffee(petition);
                break;
            case DOWN:
                break;
            default:
                System.out.println("This maybe a problem");
                break;
        }
    }

    private void buyCoffee(String type) {

        CoffeeTypes coffeeType;

        switch (type) {
            case "1":
                coffeeType = CoffeeTypes.ESPRESSO;
                serveCoffee(coffeeType);
                break;
            case "2":
                coffeeType = CoffeeTypes.LATTE;
                serveCoffee(coffeeType);
                break;
            case "3":
                coffeeType = CoffeeTypes.CAPPUCCINO;
                serveCoffee(coffeeType);
                break;
            case "back":
                System.out.println("Write action (buy, fill, take, remaining, exit):");
                stateCoffeeMachine = StateCoffeeMachine.WAITING;
                break;
            default:
                System.out.println("Action not supported");
                break;
        }

    }

    private void serveCoffee(CoffeeTypes typeCoffee) {
        if (cupsWater(typeCoffee) == 0) {
            System.out.println("Sorry, not enough water!");
        } else if (cupsMilk(typeCoffee) == 0) {
            System.out.println("Sorry, not enough milk!");
        } else if (cupsCoffee(typeCoffee) == 0) {
            System.out.println("Sorry, not enough coffee beans!");
        } else if (cups == 0) {
            System.out.println("Sorry, not enough cups!");
        } else {
            water -= typeCoffee.water;
            milk -= typeCoffee.milk;
            coffeeBeans -= typeCoffee.coffeeBeans;
            money += typeCoffee.cost;
            cups -= 1;
            System.out.println("I have enough resources, making you a coffee!");
        }

        System.out.println("Write action (buy, fill, take, remaining, exit):");
        stateCoffeeMachine = StateCoffeeMachine.WAITING;


    }

    private void printCurrentQuantities() {
        System.out.println("The coffee machine has:");
        System.out.println(water + " of water");
        System.out.println(milk + " of milk");
        System.out.println(coffeeBeans + " of coffee beans");
        System.out.println(cups + " of disposable cups");
        System.out.println(money + " of money");
    }

    private int cupsMilk(CoffeeTypes typeOfCoffee) {
        return milk / (typeOfCoffee.getMilk() == 0 ? 1 : typeOfCoffee.getMilk());
    }

    private int cupsWater(CoffeeTypes typeOfCoffee) {
        return water / (typeOfCoffee.getWater() == 0 ? 1 : typeOfCoffee.getWater());
    }

    private int cupsCoffee(CoffeeTypes typeOfCoffee) {
        return coffeeBeans / (typeOfCoffee.getCoffeeBeans() == 0 ? 1 : typeOfCoffee.getCoffeeBeans());
    }
}

enum StateCoffeeMachine {
    WAITING,
    BUYING,
    FILLING_WATER,
    FILLING_MILK,
    FILLING_COFFEE,
    FILLING_CUPS,
    DOWN;
}

enum CoffeeTypes {
    ESPRESSO(250, 0, 16, 4),
    LATTE(350, 75, 20, 7),
    CAPPUCCINO(200, 100, 12, 6);

    int water;
    int milk;
    int coffeeBeans;
    int cost;

    CoffeeTypes(int water, int milk, int coffeeBeans, int cost) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cost = cost;
    }

    public int getWater() {
        return water;
    }

    public int getMilk() {
        return milk;
    }

    public int getCoffeeBeans() {
        return coffeeBeans;
    }

    public int getCost() {
        return cost;
    }
}