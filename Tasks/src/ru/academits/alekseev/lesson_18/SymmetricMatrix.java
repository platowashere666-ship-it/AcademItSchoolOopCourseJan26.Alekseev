package ru.academits.alekseev.lesson_18;

import java.io.*;
import java.util.Arrays;

public class SymmetricMatrix implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final int[][] matrix;
    private final int size;

    public SymmetricMatrix(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Размерность матрицы должна быть > 0.");
        }

        this.size = size;
        matrix = new int[size][size];

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j <= i; ++j) {
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = matrix[i][j];
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');

        int lastComponentIndex = matrix.length - 1;

        for (int i = 0; i < lastComponentIndex; i++) {
            sb.append(Arrays.toString(matrix[i])).append(", ");
        }

        sb.append(Arrays.toString(matrix[lastComponentIndex])).append('}');

        return sb.toString();
    }

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j <= i; ++j) {
                out.writeInt(matrix[i][j]);
            }
        }
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j <= i; ++j) {
                int value = in.readInt();
                matrix[i][j] = value;
                matrix[j][i] = value;
            }
        }
    }
}
