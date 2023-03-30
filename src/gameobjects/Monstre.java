package gameobjects;

public class Monstre extends GameObject {

    private double saut; // Constante de saut (différente en fonction de la plateforme)
    private double dx; // Vitesse en x
    private int id;
    private int health;

    public Monstre(double x, double y, double w, double h, double saut, double dx, int id) {
        super(x, y, w, h);
        // 70,50 pour id =1
        // 80,90 pour id =2
        this.dx = dx;
        this.id = id;
        this.health = id;
        this.saut = saut;
    }

    public void move(Terrain t) { // Modifie la position en x
        this.setX(this.getX() + dx);
        if (this.getX() + this.getWidth() >= t.getWidth() || this.getX() <= 0) {
            dx = -dx;
        }
    }

    public int getId() {
        return id;
    }

    public boolean shot() {
        this.health--;
        return health <= 0;
    }

    public double getSaut() {
        return saut;
    }

    public void setSaut(double saut) {
        this.saut = saut;
    }
}
