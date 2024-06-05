package org.example;

public class UnionApplication {
    public static void main(String[] args) {
        String fileName = "src/main/resources/lng-big.csv";
        if (fileName.isBlank()) {
            System.out.println("Файл не указан.");
        } else {
            Union union = new Union();
            long start = System.currentTimeMillis();
            union.parse(fileName);
            long end = System.currentTimeMillis();
            float answerTime = (float) (end - start) / 1000;
            System.out.print("Время выполнения программы в секундах:");
            System.out.println(answerTime);
        }
    }
}