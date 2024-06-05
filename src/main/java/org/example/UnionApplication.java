package org.example;

public class UnionApplication {
    public static void main(String[] args) {
        String fileName = args[0];
        String path = "D:\\dev\\java-Interview-uno-soft\\src\\main\\resources\\";
        String pathFileName = path + fileName;
        if (fileName.isBlank()) {
            System.out.println("Файл не указан.");
        } else {
            Union union = new Union();
            long start = System.currentTimeMillis();
            union.parse(pathFileName);
            long end = System.currentTimeMillis();
            float answerTime = (float) (end - start) / 1000;
            System.out.print("Время выполнения программы в секундах:");
            System.out.println(answerTime);
        }
    }
}
