package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Union {
    public void parse(String path) {
        Set<String> uniqueLines = null;
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            uniqueLines = lines.collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String[]> splitLines = uniqueLines.stream()
                .map(line -> line.split(";"))
                .collect(Collectors.toList());
        UnionFind unionFind = getUnionFind(splitLines);

        Map<Integer, List<String>> groups = collectGroups(splitLines, unionFind);
        writeInFile(groups);
    }

    private static UnionFind getUnionFind(List<String[]> splitLines) {
        Map<String, Map<Integer, Integer>> value = new HashMap<>();
        UnionFind unionFind = new UnionFind(splitLines.size());
        for (int i = 0; i < splitLines.size(); i++) {
            String[] columns = splitLines.get(i);
            for (int j = 0; j < columns.length; j++) {
                if (!columns[j].equals("\"\"") && !columns[j].isBlank()) {
                    if (value.containsKey(columns[j])) {
                        Map<Integer, Integer> fromValue = value.get(columns[j]);
                        if (fromValue.containsKey(j)) {
                            unionFind.union(i, fromValue.get(j));
                        } else {
                            fromValue.put(j, i);
                            value.put(columns[j], fromValue);
                        }
                    } else {
                        Map<Integer, Integer> forValue = new HashMap<>();
                        forValue.put(j, i);
                        value.put(columns[j], forValue);
                    }
                }
            }
        }
        return unionFind;
    }

    private Map<Integer, List<String>> collectGroups(List<String[]> splitLines, UnionFind unionFind) {
        Map<Integer, List<String>> groups = new HashMap<>();
        for (int i = 0; i < splitLines.size(); i++) {
            int root = unionFind.find(i);
            List<String> lines = groups.getOrDefault(root, new ArrayList<>());
            lines.add(String.join(";", splitLines.get(i)));
            groups.put(root, lines);
        }
        return groups;
    }

    static class UnionFind {
        private int[] parent;
        private int[] rank;

        public UnionFind(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX != rootY) {
                if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
            }
        }
    }

    private void writeInFile(Map<Integer, List<String>> answerMap) {
        List<ListString> linesCopy = new ArrayList<>();
        try {
            File file = new File("D:\\dev\\java-Interview-uno-soft\\src\\main\\resources\\answerUnion.txt");
            FileWriter writer = new FileWriter(file);
            int countGroup = 0;
            int maxSizeGroup = 0;
            int countGroupWithSizeMoreThanOne = 0;
            for (List<String> lines : answerMap.values()) {
                if (lines.size() > 1) {
                    countGroupWithSizeMoreThanOne++;
                }
                linesCopy.add(new ListString(lines));
            }
            linesCopy.sort(Comparator.comparing(ListString::getSizeSet, Comparator.reverseOrder()));
            writer.write("Всего групп: " + answerMap.size());
            writer.write("\n");
            writer.write("Групп с размером больше одного: " + countGroupWithSizeMoreThanOne);
            writer.write("\n");
            for (ListString lines : linesCopy) {
                countGroup++;
                if (lines.getSizeSet() > maxSizeGroup) {
                    maxSizeGroup = lines.getSizeSet();
                }
                writer.write("Группа " + countGroup);
                writer.write("\n");
                for (String line : lines.getLineList()) {
                    writer.write(line);
                    writer.write("\n");
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
