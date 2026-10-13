import java.util.Scanner;
class MatritsaExeprion extends Exception{
    public MatritsaExeprion(String message){
        super(message);
    }
}
class CompleksnieChisla {
    private double vesh;
    private double mnim;
    public CompleksnieChisla(double vesh, double mnim) {
        this.vesh = vesh;
        this.mnim = mnim;
    }
    public double getVesh() {
        return vesh;
    }
    public double getMnim() {
        return mnim;
    }
    public CompleksnieChisla closhenie(CompleksnieChisla other) {
        return new CompleksnieChisla(this.vesh + other.vesh, this.mnim + other.mnim);
    }

    public CompleksnieChisla vichitanie(CompleksnieChisla other) {
        return new CompleksnieChisla(this.vesh - other.vesh, this.mnim - other.mnim);
    }

    public CompleksnieChisla ymn(CompleksnieChisla other) {
        double vsh = this.vesh * other.vesh - this.mnim * other.mnim;
        double mn = this.vesh * other.mnim + this.mnim * other.vesh;
        return new CompleksnieChisla(vsh, mn);
    }
    public double abssqrt() {
        return vesh * vesh + mnim * mnim;
    }

    public CompleksnieChisla delnol(CompleksnieChisla other) throws MatritsaExeprion {
        double znam = other.abssqrt();
        if (znam == 0.0) {
            throw new MatritsaExeprion("Ошибка: деление на ноль");
        }
        double vsh = (this.vesh * other.vesh + this.mnim * other.mnim) / znam;
        double mn = (this.mnim * other.vesh - this.vesh * other.mnim) / znam;
        return new CompleksnieChisla(vsh, mn);
    }

    public String toString() {
        if (mnim >= 0) {
            return vesh + " + " + mnim + "i";
        } else {
            return vesh + " - " + (-mnim) + "i";
        }
    }
}
class Matritsa {
    private int stroki;
    private int stolbtsy;
    private CompleksnieChisla[][] matr;

    public Matritsa(int stroki, int stolbtsy) {
        this.stolbtsy = stolbtsy;
        this.stroki = stroki;
        this.matr = new CompleksnieChisla[stroki][stolbtsy];
        for (int i = 0; i < stroki; i++) {
            for (int j = 0; j < stolbtsy; j++) {
                this.matr[i][j] = new CompleksnieChisla(0.0, 0.0);
            }
        }
    }

    public int getStroki() {
        return stroki;
    }

    public int getStolbtsy() {
        return stolbtsy;
    }

    public CompleksnieChisla get(int i, int j) {
        return matr[i][j];
    }

    public void set(int i, int j, CompleksnieChisla val) {
        matr[i][j] = val;
    }

    public Matritsa closhenie(Matritsa other) throws MatritsaExeprion {
        if (this.stroki != other.stroki || this.stolbtsy != other.stolbtsy) {
            throw new MatritsaExeprion("Нельзя сложить из за того что матрицы разного размера");
        }
        Matritsa res = new Matritsa(this.stroki, this.stolbtsy);
        for (int i = 0; i < this.stroki; i++) {
            for (int j = 0; j < this.stolbtsy; j++) {
                res.matr[i][j] = this.matr[i][j].closhenie(other.matr[i][j]);
            }
        }
        return res;
    }

    public Matritsa transponirovat() {
        Matritsa res = new Matritsa(this.stolbtsy, this.stroki);
        for (int i = 0; i < this.stroki; i++) {
            for (int j = 0; j < this.stolbtsy; j++) {
                res.matr[j][i] = this.matr[i][j];
            }
        }
        return res;
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < stroki; i++) {
            s = s + "[ ";
            for (int j = 0; j < stolbtsy; j++) {
                s = s + matr[i][j];
                if (j < stolbtsy - 1) {
                    s = s + " ; ";
                }
            }
            s = s + " ]\n";
        }
        return s;
    }
    public Matritsa ymnoshenie(Matritsa other) throws MatritsaExeprion {
        if (this.stolbtsy != other.stroki) {
            throw new MatritsaExeprion("Умножить нельзя столбцы первой матрицы != строки второй матрицы");
        }
        Matritsa res = new Matritsa(this.stroki, other.stolbtsy);
        for (int i = 0; i < this.stroki; i++) {
            for (int j = 0; j < other.stolbtsy; j++) {
                CompleksnieChisla sum = new CompleksnieChisla(0.0, 0.0);
                for (int k = 0; k < this.stolbtsy; k++) {
                    sum = sum.closhenie(this.matr[i][k].ymn(other.matr[k][j]));
                }
                res.matr[i][j] = sum;
            }
        }
        return res;
    }

    public CompleksnieChisla opredelitel() throws MatritsaExeprion {
        if (this.stroki != this.stolbtsy) {
            throw new MatritsaExeprion("Определитель существует только для квадратных матриц");
        }
        int n = this.stroki;
        CompleksnieChisla[][] a = new CompleksnieChisla[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = this.matr[i][j];
            }
        }
        CompleksnieChisla det = new CompleksnieChisla(1.0, 0.0);
        for (int i = 0; i < n; i++) {
            int pivot = -1;
            for (int row = i; row < n; row++) {
                if (a[row][i].abssqrt() > 0.0) {
                    pivot = row;
                    break;
                }
            }
            if (pivot == -1) {
                return new CompleksnieChisla(0.0, 0.0);
            }
            if (pivot != i) {
                for (int j = 0; j < n; j++) {
                    CompleksnieChisla temp = a[i][j];
                    a[i][j] = a[pivot][j];
                    a[pivot][j] = temp;
                }
                det = det.ymn(new CompleksnieChisla(-1.0, 0.0));
            }
            det = det.ymn(a[i][i]);
            for (int row = i + 1; row < n; row++) {
                if (a[row][i].abssqrt() > 0.0) {
                    CompleksnieChisla factor = a[row][i].delnol(a[i][i]);
                    for (int col = i; col < n; col++) {
                        a[row][col] = a[row][col].vichitanie(factor.ymn(a[i][col]));
                    }
                }
            }
        }
        return det;
    }
    private Matritsa podmatr(int l, int e) {
        Matritsa sub = new Matritsa(this.stroki - 1, this.stolbtsy - 1);
        int r = 0;
        for (int i = 0; i < this.stroki; i++) {
            if (i == l) continue;
            int c = 0;
            for (int j = 0; j < this.stolbtsy; j++) {
                if (j == e) continue;
                sub.matr[r][c] = this.matr[i][j];
                c++;
            }
            r++;
        }
        return sub;
    }
    public Matritsa obratnaya() throws MatritsaExeprion {
        if (this.stroki != this.stolbtsy) {
            throw new MatritsaExeprion("Матрица не квадратная, нельзя найти обратную");
        }
        int n = this.stroki;
        if (n == 1) {
            if (this.matr[0][0].abssqrt() == 0.0) {
                throw new MatritsaExeprion("Матрица вырождена, обратной нет");
            }
            Matritsa res = new Matritsa(1, 1);
            res.matr[0][0] = new CompleksnieChisla(1.0, 0.0).delnol(this.matr[0][0]);
            return res;
        }
        CompleksnieChisla det = this.opredelitel();
        if (det.abssqrt() == 0.0) {
            throw new MatritsaExeprion("Матрица вырождена т.е. det = 0, обратная матрица не существует");
        }
        Matritsa res = new Matritsa(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Matritsa minorMatr = this.podmatr(i, j);
                CompleksnieChisla minorDet = minorMatr.opredelitel();
                if ((i + j) % 2 != 0) {
                    minorDet = minorDet.ymn(new CompleksnieChisla(-1.0, 0.0));
                }
                res.matr[j][i] = minorDet.delnol(det);
            }
        }

        return res;
    }
    public Matritsa delenie(Matritsa other) throws MatritsaExeprion {
        Matritsa invB = other.obratnaya();
        return this.ymnoshenie(invB);
    }
}
public class Main{
    private static Matritsa entermatr(Scanner sc, String name) {
        System.out.println("\n~~~ Ввод матрицы " + name + " ~~~");
        System.out.print("Введите количество строк: ");
        int r = sc.nextInt();
        System.out.print("Введите количество столбцов: ");
        int c = sc.nextInt();

        Matritsa m = new Matritsa(r, c);
        System.out.println("Введите элементы (сначала введите вещественную, затем мнимую часть):");
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                System.out.print("Элемент вида [" + i + "][" + j + "] (Re Im): ");
                double re = sc.nextDouble();
                double im = sc.nextDouble();
                m.set(i, j, new CompleksnieChisla(re, im));
            }
        }
        return m;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n~~~ МЕНЮ ОПЕРАЦИЙ С МАТРИЦАМИ ~~~");
            System.out.println("1. Сложение двух матриц (A + B)");
            System.out.println("2. Умножение двух матриц (A * B)");
            System.out.println("3. Деление матриц (A / B)");
            System.out.println("4. Транспонирование матрицы");
            System.out.println("5. Определитель матрицы");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");
            int choice = sc.nextInt();
            if (choice == 0) {
                System.out.println("Программа завершена");
                break;
            }
            try {
                Matritsa a, b;
                switch (choice) {
                    case 1:
                        a = entermatr(sc, "A");
                        b = entermatr(sc, "B");
                        System.out.println("\n Результат A + B:");
                        System.out.println(a.closhenie(b));
                        break;
                    case 2:
                        a = entermatr(sc, "A");
                        b = entermatr(sc, "B");
                        System.out.println("\n Результат A * B:");
                        System.out.println(a.ymnoshenie(b));
                        break;
                    case 3:
                        a = entermatr(sc, "A");
                        b = entermatr(sc, "B");
                        System.out.println("\n Результат A / B (A * B^-1):");
                        System.out.println(a.delenie(b));
                        break;
                    case 4:
                        a = entermatr(sc, "A");
                        System.out.println("\n Транспонированная матрица:");
                        System.out.println(a.transponirovat());
                        break;
                    case 5:
                        a = entermatr(sc, "A");
                        System.out.println("\n Определитель матрицы = " + a.opredelitel());
                        break;
                    default:
                        System.out.println("Неверный пункт");
                }
            } catch (MatritsaExeprion e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
        sc.close();
    }
}

