import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Stock {
    private String symbol;
    private String name;
    private double price;

    public Stock(String symbol, String name, double price) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}

class Portfolio {
    private Map<Stock, Integer> holdings;

    public Portfolio() {
        this.holdings = new HashMap<>();
    }

    public void buyStock(Stock stock, int quantity) {
        holdings.put(stock, holdings.getOrDefault(stock, 0) + quantity);
    }

    public void sellStock(Stock stock, int quantity) {
        if (holdings.containsKey(stock) && holdings.get(stock) >= quantity) {
            holdings.put(stock, holdings.get(stock) - quantity);
            if (holdings.get(stock) == 0) {
                holdings.remove(stock);
            }
        } else {
            System.out.println("Not enough shares to sell!");
        }
    }

    public double getPortfolioValue() {
        return holdings.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    public void displayPortfolio() {
        System.out.println("Current Portfolio:");
        for (Map.Entry<Stock, Integer> entry : holdings.entrySet()) {
            System.out.println("Stock: " + entry.getKey().getSymbol() +
                    ", Quantity: " + entry.getValue() +
                    ", Value: " + (entry.getKey().getPrice() * entry.getValue()));
        }
        System.out.println("Total Portfolio Value: " + getPortfolioValue());
    }
}

class Market {
    private List<Stock> stocks;

    public Market() {
        this.stocks = new ArrayList<>();
        // Add some stocks to the market
        stocks.add(new Stock("AAPL", "Apple Inc.", 150.00));
        stocks.add(new Stock("GOOGL", "Alphabet Inc.", 2800.00));
        stocks.add(new Stock("AMZN", "Amazon.com, Inc.", 3400.00));
        stocks.add(new Stock("TSLA", "Tesla, Inc.", 700.00));
    }

    public Stock getStockBySymbol(String symbol) {
        for (Stock stock : stocks) {
            if (stock.getSymbol().equalsIgnoreCase(symbol)) {
                return stock;
            }
        }
        return null;
    }

    public void displayMarketData() {
        System.out.println("Current Market Data:");
        for (Stock stock : stocks) {
            System.out.println(stock);
        }
    }

    public void updateStockPrice(String symbol, double newPrice) {
        Stock stock = getStockBySymbol(symbol);
        if (stock != null) {
            stock.setPrice(newPrice);
        }
    }
}

public class StockTradingPlatform {
    public static void main(String[] args) {
        Market market = new Market();
        Portfolio portfolio = new Portfolio();
        Scanner scanner = new Scanner(System.in);
        String command;

        while (true) {
            System.out.println("\nStock Trading Platform");
            System.out.println("+-------------------------------------------------------------------+");
            System.out.println("1. Display Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. Display Portfolio");
            System.out.println("5. Update Stock Price");
            System.out.println("6. Exit");
            System.out.println("+-------------------------------------------------------------------+");
            System.out.print("Enter your choice: ");
            command = scanner.nextLine();

            switch (command) {
                case "1":
                    market.displayMarketData();
                    break;
                case "2":
                    System.out.print("Enter stock symbol: ");
                    String symbol = scanner.nextLine();
                    Stock stockToBuy = market.getStockBySymbol(symbol);
                    if (stockToBuy != null) {
                        System.out.print("Enter quantity to buy: ");
                        int quantity = Integer.parseInt(scanner.nextLine());
                        portfolio.buyStock(stockToBuy, quantity);
                    } else {
                        System.out.println("Stock not found!");
                    }
                    break;
                case "3":
                    System.out.print("Enter stock symbol: ");
                    String symbolToSell = scanner.nextLine();
                    Stock stockToSell = market.getStockBySymbol(symbolToSell);
                    if (stockToSell != null) {
                        System.out.print("Enter quantity to sell: ");
                        int quantityToSell = Integer.parseInt(scanner.nextLine());
                        portfolio.sellStock(stockToSell, quantityToSell);
                    } else {
                        System.out.println("Stock not found!");
                    }
                    break;
                case "4":
                    portfolio.displayPortfolio();
                    break;
                case "5":
                    System.out.print("Enter stock symbol to update: ");
                    String stockSymbol = scanner.nextLine();
                    System.out.print("Enter new stock price: ");
                    double newPrice = Double.parseDouble(scanner.nextLine());
                    market.updateStockPrice(stockSymbol, newPrice);
                    break;
                case "6":
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
