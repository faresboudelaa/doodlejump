package gameobjects;

// Import de packages java :
import java.util.*;

// Le personnage est un objet avec vitesse.
public class Personnage extends GameObject {

    private double dx, dy; // Vitesse en x et en y
    // isRight/Left gère les boutons appuyés, isInert gère le relâchement
    // isShoot & canShoot indique si l'on tire/peut tirer un projectile.
    private boolean isRight, isInertRight, isLeft, isInertLeft, isShoot, canShoot;
    private boolean collides=true;
    private ArrayList<Projectile> listProjectiles; // Stock tous les projectiles du personnage encore sur le terrain

    public Personnage(double x, double y, double w, double h, double dy) {
        super(x, y, w, h);
        this.dy = dy;
        this.dx = 0;
        this.isRight = false;
        this.isLeft = false;
        this.isInertRight = false;
        this.isInertLeft = false;
        this.isShoot = false;
        this.canShoot = true;
        this.listProjectiles = new ArrayList<Projectile>();
    }

    // Méthodes de la classe




    // Colision entre le personnage et une plateforme
    public void collides_plateforme(Plateforme pf, double deltaTime) {
        if(!collides) return;
        if ((this.getX() + (this.getWidth() * 0.65) >= pf.getX()) // si ça ne dépasse pas par la gauche de la
                // plateforme. + witdh*0.65 sert à ne compter que le x du dernier pied
                && (this.getX() + (this.getWidth() * 0.25) <= pf.getX() + pf.getWidth())
                // si ça ne dépasse pas par la droite de la plateforme.
                // + witdh*0.25 sert à ne compter que le x du premier pied
                && (this.getY() + 0.87 * this.getHeight() >= pf.getY())
                && (this.getY() + 0.87 * this.getHeight() <= pf.getY() + pf.getHeight())
                && (this.getDy() > 0)) { // Si le personnage descent
                    if(pf.getItem()==null){
                        dy = pf.getSaut() * deltaTime;
                    }else 
                        this.collides_item(pf.getItem(), deltaTime);
                        dy = pf.getSaut() * deltaTime;
        }
    }

    // Colision entre le personnage et un item
    // public void collides_item(Items it, double deltaTime) {
        // if(!collides) return;
        // if (it.getNeedsHead()) {
        //     //0.43 c le ccentre .87 centre vertical 
        //     if ((this.getX() + (this.getWidth() * 0.65) >= it.getX()) 
        //     // si ça ne dépasse pas par la gauche de l'item.
        //             // + witdh*0.65 sert à ne compter que le x du dernier pied
        //             && (this.getX() + (this.getWidth() * 0.25) <= it.getX() + it.getWidth())
        //             // si ça ne dépasse pas par la droite de la item.
        //             // + witdh*0.25 sert à ne compter que le x du premier pied
        //             && (this.getY() + 0.87 * this.getHeight() >= it.getY())
        //             && (this.getY() + 0.87 * this.getHeight() <= it.getY() + it.getHeight())) { // Si le personnage descend
        //                 it.runEffect(this);
        //         it=null;
        //         System.out.println("test need head");
        //     }
   /*  public void collides_item(Items it, double deltaTime) {
        double epsilone=5;
        boolean ver= (((Math.abs(it.getX()-this.getX())<epsilone) && Math.abs(it.getX()-(this.getX()+this.getWidth()))<epsilone)
        ||(Math.abs(this.getX()-(it.getX()+it.getWidth()))<epsilone && Math.abs(this.getX()+this.getWidth()-(it.getX()+it.getWidth()))<epsilone));
        boolean horHead=   (((Math.abs(it.getY()-this.getY())<epsilone) && Math.abs(it.getY()-(this.getY()+this.getHeight()*0.13))<epsilone)||(Math.abs(this.getY()-(it.getY()+it.getHeight()))<epsilone && Math.abs(this.getY()+this.getHeight()*0.13-(it.getY()+it.getHeight()))<epsilone));
        boolean horNoHead= (((Math.abs(it.getY()-this.getY())<epsilone) && Math.abs(it.getY()-(this.getY()+this.getHeight()))<epsilone)||     (Math.abs(this.getY()-(it.getY()+it.getHeight()))<epsilone && Math.abs(this.getY()+this.getHeight()-(it.getY()+it.getHeight()))<epsilone));
        if (ver && collides) if ((it.getNeedsHead() && horHead ) ||(!it.getNeedsHead() && horNoHead) ){
            it.runEffect(this);
            it=null;
            collides=true;
        }
    }
 */

 public void collides_item(Items m, double deltaTime) {
    double epsilone = 5;
    boolean ver = (Math.abs((m.getY() + m.getHeight() / 2) - (this.getY() + this.getHeight() / 2)) < ((m.getHeight() + this.getHeight()) / 2 + epsilone));
    boolean horHead = (Math.abs((m.getX() + m.getWidth() / 2) - (this.getX() + this.getWidth() / 2)) < ((m.getWidth() + this.getWidth()) / 2 + epsilone));
    boolean horNoHead = (Math.abs((m.getX() + m.getWidth() / 2) - (this.getX() + this.getWidth() / 2)) < ((m.getWidth() + this.getWidth()) / 2 + epsilone)) 
                  && m.getY() + m.getHeight() / 2 < this.getY() + this.getHeight() / 2;
    if (ver && collides) {
        if ((m.getNeedsHead() && horHead) || (!m.getNeedsHead() && horNoHead)) {
            m.runEffect(this);
            m = null;
            collides = true;
        }
    }
}
    public boolean collides_monstre(Monstre m) {
        /*
        double epsilone=3;
        boolean ver= (((Math.abs(m.getX()-this.getX())<epsilone) && Math.abs(m.getX()-(this.getX()+this.getWidth()))<epsilone)
        ||(Math.abs(this.getX()-(m.getX()+m.getWidth()))<epsilone && Math.abs(this.getX()+this.getWidth()-(m.getX()+m.getWidth()))<epsilone));
        boolean hor= (((Math.abs(m.getY()-this.getY())<epsilone)  && Math.abs(m.getY()-(this.getY()+this.getHeight()))<epsilone)
        ||(Math.abs(this.getY()-(m.getY()+m.getHeight()))<epsilone && Math.abs(this.getY()+this.getHeight()-(m.getY()+m.getHeight()))<epsilone));if(ver || hor) System.out.println("ver ="+ver+", hor="+hor);
       */
        double epsilone = 0.2;
        boolean ver = (Math.abs((m.getY() + m.getHeight() / 2) - (this.getY() + this.getHeight() / 2)) < ((m.getHeight() + this.getHeight()) / 2 + epsilone));
        boolean hor = (Math.abs((m.getX() + m.getWidth() / 2) - (this.getX() + this.getWidth() / 2)) < ((m.getWidth() + this.getWidth()) / 2 + epsilone));
        return (ver && hor);   
    }

    // Tir un projectile avec ce personnage
    public void tirer(double w, double h, double vx, double vy) {
        this.listProjectiles.add(new Projectile(this.getX() + this.getWidth() * 0.43, this.getY(), w, h, vx, vy));
    }

    public boolean projectileCollide(Monstre m){
/*         double epsilone=5;
        for (Projectile p :listProjectiles){
        boolean ver= (((Math.abs(m.getX()-p.getX())<epsilone) && Math.abs(m.getX()-(p.getX()+p.getWidth()))<epsilone)
        ||(Math.abs(p.getX()-(m.getX()+m.getWidth()))<epsilone && Math.abs(p.getX()+p.getWidth()-(m.getX()+m.getWidth()))<epsilone));
        boolean hor= (((Math.abs(m.getY()-p.getY())<epsilone)  && Math.abs(m.getY()-(p.getY()+p.getHeight()))<epsilone)
        ||(Math.abs(p.getY()-(m.getY()+m.getHeight()))<epsilone && Math.abs(p.getY()+p.getHeight()-(m.getY()+m.getHeight()))<epsilone));
            if (ver && hor){ listProjectiles.remove(p);
                return true;}
        }
        return false;
*/
        double epsilone = 3;
        for (Projectile p :listProjectiles){
            boolean ver = (Math.abs((m.getY() + m.getHeight() / 2) - (p.getY() + p.getHeight() / 2)) < ((m.getHeight() + p.getHeight()) / 2 + epsilone));
            boolean hor = (Math.abs((m.getX() + m.getWidth() / 2) - (p.getX() + p.getWidth() / 2)) < ((m.getWidth() + p.getWidth()) / 2 + epsilone));
            if (ver && hor){ listProjectiles.remove(p);
                return true;}
        }
        return false;

    }
    
    public void dead(){
        collides=false;
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Personnage.this.setDy(0);
                // p.setCollide(false);
            }
        };
        timer.schedule(task, 3000);
        this.setDy(3);
        // p.setCollide(true);

        // pour le debug:
        // try {
        //     wait(1000*30);
        // } catch (InterruptedException e) {
        //     System.out.println("Personnage.dead()");
        //     e.printStackTrace();
        // }
    }


    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean isRight) {
        this.isRight = isRight;
    }

    public boolean isInertRight() {
        return isInertRight;
    }

    public void setInertRight(boolean isInertRight) {
        this.isInertRight = isInertRight;
    }

    public boolean isLeft() {
        return isLeft;
    }

    public void setLeft(boolean isLeft) {
        this.isLeft = isLeft;
    }

    public boolean isInertLeft() {
        return isInertLeft;
    }

    public void setInertLeft(boolean isInertLeft) {
        this.isInertLeft = isInertLeft;
    }

    public boolean isShoot() {
        return isShoot;
    }

    public void setShoot(boolean isShoot) {
        this.isShoot = isShoot;
    }

    public ArrayList<Projectile> getListProjectiles() {
        return listProjectiles;
    }

    public void setListProjectiles(ArrayList<Projectile> listProjectiles) {
        this.listProjectiles = listProjectiles;
    }

    public boolean iscanShoot() {
        return canShoot;
    }

    public void setcanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }

                
    public void setCollide(boolean p){
        collides=p;
    }
}