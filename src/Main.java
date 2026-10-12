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

    public Matritsa(CompleksnieChisla[][] massiv) {
        this.stroki = massiv.length;
        this.stolbtsy = massiv[0].length;
        this.matr = new CompleksnieChisla[stroki][stolbtsy];
        for (int i = 0; i < stroki; i++) {
            for (int j = 0; j < stolbtsy; j++) {
                this.matr[i][j] = massiv[i][j];
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
}

