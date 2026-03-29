package ru.academits.alekseev.lesson_18;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("Tasks/src/ru/academits/alekseev/lesson_18/output2.bin"))) {
            SymmetricMatrix symmetricMatrix1 = new SymmetricMatrix(3);
            System.out.println(symmetricMatrix1);

            out.writeObject(symmetricMatrix1);
        }

        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("Tasks/src/ru/academits/alekseev/lesson_18/output2.bin"))) {
            SymmetricMatrix symmetricMatrix2 = (SymmetricMatrix) in.readObject();

            System.out.println(symmetricMatrix2);
        }
    }
}
