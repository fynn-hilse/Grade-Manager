import java.util.Scanner;

public class GradeManager {

    // Konstante für das Array-Limit, damit man es leicht ändern kann
    private static final int MAX_STUDENTS = 30;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Arrays initialisieren
        String[] namen = new String[MAX_STUDENTS];
        double[] noten = new double[MAX_STUDENTS];
        int studentenAnzahl = 0;

        System.out.println("--- Noten-Manager v1.0 ---");

        while (studentenAnzahl < MAX_STUDENTS) {
            System.out.print("Name eingeben (oder 'beenden'): ");
            String eingabeName = scanner.next();

            if (eingabeName.equalsIgnoreCase("beenden")) {
                break;
            }

            System.out.print("Note eingeben (1.0 - 6.0): ");
            // Checken, ob wirklich eine Zahl kommt, um Absturz zu verhindern
            if (!scanner.hasNextDouble()) {
                System.out.println("Fehler: Bitte eine gültige Kommazahl eingeben (z.B. 2,3).");
                scanner.next(); // Falschen Input konsumieren
                continue;
            }
            
            double eingabeNote = scanner.nextDouble();

            // Validierung
            if (eingabeNote < 1.0 || eingabeNote > 6.0) {
                System.out.println("Fehler: Note muss zwischen 1,0 und 6,0 liegen!");
                continue;
            }

            // Speichern
            namen[studentenAnzahl] = eingabeName;
            noten[studentenAnzahl] = eingabeNote;
            studentenAnzahl++;
        }

        scanner.close();

        // Wenn keine Studenten eingegeben wurden, abbrechen
        if (studentenAnzahl == 0) {
            System.out.println("Keine Daten eingegeben. Programm beendet.");
            return;
        }

        // Verarbeitung
        sortiereNoten(noten, studentenAnzahl, namen);
        printErgebnisse(noten, namen, studentenAnzahl);
    }

    public static void sortiereNoten(double[] noten, int anzahl, String[] namen) {
        // Bubble Sort
        for (int durchgang = 0; durchgang < anzahl; durchgang++) {
            boolean getauscht = false;
            for (int i = 0; i < anzahl - 1; i++) {
                // Sortierung aufsteigend (beste Note zuerst)
                if (noten[i] > noten[i + 1]) {
                    // Swap Note
                    double tempNote = noten[i];
                    noten[i] = noten[i + 1];
                    noten[i + 1] = tempNote;

                    // Swap Name (WICHTIG: Name muss mitwandern!)
                    String tempName = namen[i];
                    namen[i] = namen[i + 1];
                    namen[i + 1] = tempName;

                    getauscht = true;
                }
            }
            // Optimierung: Wenn nichts getauscht wurde, sind wir fertig
            if (!getauscht) {
                break;
            }
        }
    }

    public static double berechneNotenschnitt(double[] noten, int anzahl) {
        double summe = 0;
        for (int i = 0; i < anzahl; i++) {
            summe += noten[i];
        }
        return summe / anzahl;
    }

    public static double findeBesteNote(double[] noten, int anzahl) {
        double besteAktuell = 6.0;
        for (int i = 0; i < anzahl; i++) {
            if (noten[i] < besteAktuell) {
                besteAktuell = noten[i];
            }
        }
        return besteAktuell;
    }
    
    // Hilfsmethode für saubere Ausgabe
    public static void printErgebnisse(double[] noten, String[] namen, int anzahl) {
        System.out.println("\n--- Ergebnisse (Sortiert) ---");
        for (int i = 0; i < anzahl; i++) {
            System.out.println(namen[i] + ": " + noten[i]);
        }
        System.out.println("-----------------------------");
        System.out.println("Beste Note: " + findeBesteNote(noten, anzahl));
        System.out.printf("Durchschnitt: %.2f\n", berechneNotenschnitt(noten, anzahl));
    }
}
