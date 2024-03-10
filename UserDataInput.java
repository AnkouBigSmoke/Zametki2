import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class UserDataInput {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Введите данные (Фамилия Имя Отчество дата_рождения номер_телефона пол):");
            String input = scanner.nextLine();

            String[] userData = input.split(" ");
            if (userData.length != 6) {
                throw new InputDataException("Неверное количество данных");
            }

            String lastName = userData[0];
            String firstName = userData[1];
            String middleName = userData[2];
            String birthDateStr = userData[3];
            String phoneNumberStr = userData[4];
            String genderStr = userData[5];

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date birthDate = dateFormat.parse(birthDateStr);

            long phoneNumber = Long.parseLong(phoneNumberStr);

            if (!genderStr.equals("f") && !genderStr.equals("m")) {
                throw new InputDataException("Неверное значение пола");
            }

            String fileName = lastName + ".txt";
            try (PrintWriter writer = new PrintWriter(new FileWriter(new File(fileName), true))) {
                writer.printf("%s %s %s %s %d %s%n", lastName, firstName, middleName, birthDateStr, phoneNumber, genderStr);
            } catch (IOException e) {
                throw new FileOperationException("Ошибка при записи в файл", e);
            }

            System.out.println("Данные успешно записаны в файл: " + fileName);
        } catch (InputDataException | ParseException | NumberFormatException | FileOperationException e) {
            System.err.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    static class InputDataException extends Exception {
        public InputDataException(String message) {
            super(message);
        }
    }

    static class FileOperationException extends Exception {
        public FileOperationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
