package cstjean.mobile.jeudedame.code;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static cstjean.mobile.jeudedame.code.DamierUtilitaire.transformerPositionEnManoury;

/**
 * Cette classe est le damier qui contient tous les emplacement de pions et les pions
 *
 * @author Sebastien Fortier
 * @author Yoan Gauthier
 */
public class Damier {

    /**
     * Instance du singleton
     */
    private static Damier m_instance = null;
    /**
     * Liste contenant la notation manoury de chaque mouvement
     */
    private final List<String> m_listeManouryMouvement = new LinkedList<>();

    /**
     * Structure contenant les pions et leurs endroits sur le jeu
     */
    private final Map<Tuile, Pion> m_tuiles = new LinkedHashMap<>();
    /**
     * Détermine si c'est le tour du joueur blanc
     */
    private boolean m_estTourJoueurBlanc = true;

    /**
     * Constructeur du damier
     */
    private Damier() {
        initialiser();
    }

    /**
     * Permet d'accéder à l'instance du singleton et la crée
     * si elle n'existe pas
     *
     * @return l'instance du singleton Jeu
     */
    public static Damier getInstance() {
        if (m_instance == null) {
            m_instance = new Damier();
        }
        return m_instance;
    }

    /**
     * Permet d'initialiser le Damier avec les 40 pions et les différents emplacements
     */
    public void initialiser() {
        for (int y = 0; y < 10; y += 2) {
            for (int x = 0; x < 10; x++) {
                Tuile tuile = new Tuile(x, y);

                if (x % 2 == 0) {
                    ajouterPion(tuile, null);
                } else {
                    if (y < 4) {
                        ajouterPion(tuile, new Pion(Pion.Couleur.NOIR));
                    } else if (y > 5) {
                        ajouterPion(tuile, new Pion(Pion.Couleur.BLANC));
                    } else {
                        ajouterPion(tuile, null);
                    }
                }


            }
        }
        for (int y = 1; y < 10; y += 2) {
            for (int x = 0; x < 10; x++) {
                Tuile tuile = new Tuile(x, y);

                if (x % 2 == 0) {

                    if (y < 4) {
                        ajouterPion(tuile, new Pion(Pion.Couleur.NOIR));
                    } else if (y > 6) {
                        ajouterPion(tuile, new Pion(Pion.Couleur.BLANC));
                    } else {
                        ajouterPion(tuile, null);
                    }
                } else {
                    ajouterPion(tuile, null);


                }/*
                    blanc 4 x 5 y
                    noir  5 x 4 y
                    noir  7 x 2 y
                    noir  3 x 4 y*/

            }
        }


        m_estTourJoueurBlanc = true;
        Set<Tuile> tuiles = m_tuiles.keySet();
        for (Tuile tuile : tuiles) {
            populerTuile(tuile);
        }
        for (Tuile tuile : tuiles) {
            supprimerPion(tuile);
        }


        ajouterPion(new Tuile(4,5), new Pion(Pion.Couleur.BLANC));
        ajouterPion(new Tuile(5,4), new Pion(Pion.Couleur.NOIR));
        ajouterPion(new Tuile(7,2),new Pion(Pion.Couleur.NOIR));
        ajouterPion(new Tuile(3,4), new Pion(Pion.Couleur.NOIR));
       // detecterNbMovement(getTuile(4,5), 0);




    }

    private void populerTuile(Tuile p_tuile){
        boolean faitPartiContour = false;
        if(p_tuile.getY() == 0 ){
            if(p_tuile.getX() != 0){
                p_tuile.setTuileBasGauche(getTuile(p_tuile.getX() - 1, p_tuile.getY() + 1));
            }
           faitPartiContour = true;
           if(p_tuile.getX() != 9){
               p_tuile.setTuileBasDroite(getTuile(p_tuile.getX() + 1, p_tuile.getY() + 1));
           }
        }
        if(p_tuile.getY() == 9 ){
            if(p_tuile.getX() != 0){
                p_tuile.setTuileHautGauche(getTuile(p_tuile.getX() - 1, p_tuile.getY() - 1));
            }
            faitPartiContour = true;
            if(p_tuile.getX() != 9){
                p_tuile.setTuileHautDroite(getTuile(p_tuile.getX() + 1, p_tuile.getY() - 1));
            }
        }
        if(p_tuile.getX() == 0){
            faitPartiContour = true;
            if(p_tuile.getY() != 0){
                p_tuile.setTuileHautDroite(getTuile(p_tuile.getX() + 1, p_tuile.getY() - 1));
            }
            if(p_tuile.getY() != 9){
                p_tuile.setTuileBasDroite(getTuile(p_tuile.getX() + 1, p_tuile.getY() + 1));
            }
        }
        if(p_tuile.getX() == 9){
            faitPartiContour = true;
            if(p_tuile.getY() != 0){
                p_tuile.setTuileHautGauche(getTuile(p_tuile.getX() - 1, p_tuile.getY() - 1));
            }
            if(p_tuile.getY() != 9){
                p_tuile.setTuileBasGauche(getTuile(p_tuile.getX() - 1, p_tuile.getY() + 1));
            }
        }
        if(!faitPartiContour){
            p_tuile.setTuileBasGauche(getTuile(p_tuile.getX() - 1, p_tuile.getY() + 1));
            p_tuile.setTuileHautGauche(getTuile(p_tuile.getX() - 1, p_tuile.getY() - 1));
            p_tuile.setTuileBasDroite(getTuile(p_tuile.getX() + 1, p_tuile.getY() + 1));
            p_tuile.setTuileHautDroite(getTuile(p_tuile.getX() + 1, p_tuile.getY() - 1));
        }

    }

    /**
     * Cette méthode permet d'ajouter un Pion dans le damier
     *
     * @param p_pion pion que l'on désire placer
     */
    public void ajouterPion(Tuile p_tuile, Pion p_pion) {
        m_tuiles.put(p_tuile, p_pion);
    }


    /**
     * Permet d'avoir accès à la Map contenant les tuiles et les pions
     *
     * @return la Map du jeu de dame
     */
    public Map<Tuile, Pion> getTuiles() {
        return m_tuiles;
    }

    /**
     * Permet d'obtenir la liste qui contient les mouvements en notation manoury
     *
     * @return la liste des mouvements en notation manoury
     */
    public List<String> getlisteManouryMouvement() {
        return m_listeManouryMouvement;
    }

    /**
     * Permet de savoir si c'est le tour du joueur blanc
     *
     * @return vrai si c'est le tour du joueur blanc
     * faux si c'est le tour du joueur noir
     */
    public boolean getestTourJoueurBlanc() {
        return m_estTourJoueurBlanc;
    }

    /**
     * Transforme un pion en pion de type Dame
     *
     * @param p_tuile tuile qui contient le pion qu'on veut transformer en dame
     */
    public void transformerPion(Tuile p_tuile) {
        if (m_tuiles.get(p_tuile) != null) {
            Dame dame = new Dame(Objects.requireNonNull(m_tuiles.get(p_tuile)).getCouleur());
            ajouterPion(p_tuile, dame);
        }

    }

    /**
     * Permet de compter il y a combien de pions d'une couleur spécifique
     *
     * @param p_couleur la couleur des pions qu'on veut compter
     * @return le nombre de pions de la couleur reçu en paramètre
     */
    public int compterCouleur(Pion.Couleur p_couleur) {
        int compteur = 0;
        Tuile tuile = new Tuile(0, 0);
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                tuile.setX(x);
                tuile.setY(y);
                if (m_tuiles.get(tuile) != null) {
                    if (Objects.requireNonNull(m_tuiles.get(tuile)).
                            getCouleur().equals(p_couleur)) {
                        compteur++;
                    }
                }
            }
        }
        return compteur;
    }

    /**
     * Cette méthode permet d'avoir le nombre de pion Total dans le damier
     *
     * @return le nb de pions
     */
    public int getNbPions() {

        int compteurPion = 0;
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                Tuile tuile = new Tuile(x, y);
                if (m_tuiles.get(tuile) != null) {
                    compteurPion++;
                }
            }
        }
        return compteurPion;
    }

    /**
     * Permet d'obtenir un pion selon son index sur le damier
     *
     * @return le pion selon l'index indiqué
     */
    public Pion getPion(Tuile p_tuile) {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if (x == p_tuile.getX() && y == p_tuile.getY()) {
                    return m_tuiles.get(p_tuile);
                }
            }
        }
        return null;
    }

    /**
     * Permet d'obtenir la couleur du pion qui est sur une tuile
     *
     * @param p_tuile la tuile d'on on veut regarder la couleur du pion
     * @return la couleur du pion sur une tuile
     */
    public Pion.Couleur getCouleurPionSurTuile(Tuile p_tuile) {
        return getPion(p_tuile).getCouleur();
    }

    /**
     * Effectue le déplacement d'un pion : supprime le pion à l'ancien emplacement et ajoute celui
     * reçu en paramètre à la position désirée
     *
     * @param p_pion          le pion qu'on veut déplacer
     * @param p_ancienneTuile sa position initiale
     * @param p_nouvelleTuile la nouvelle position du pion
     */
    public void deplacerPion(Pion p_pion, Tuile p_ancienneTuile, Tuile p_nouvelleTuile) {
        m_tuiles.put(p_nouvelleTuile, p_pion);
        m_tuiles.put(p_ancienneTuile, null);
        m_estTourJoueurBlanc = !m_estTourJoueurBlanc;
        if (p_pion.estBlanc()) {
            if (p_nouvelleTuile.getY() == 0) {
                transformerPion(p_nouvelleTuile);
            }
        } else {
            if (p_nouvelleTuile.getY() == 9) {
                transformerPion(p_nouvelleTuile);
            }
        }

    }

    /**
     * Permet d'obtenir une tuile sur le damier selon ses coordonées
     *
     * @param p_x la coordonée en X de la tuile
     * @param p_y la coordonée en Y de la tuile
     * @return la tuile selon les coordonnées données en paramètre
     */
    public Tuile getTuile(int p_x, int p_y) {
        Set<Tuile> tuiles = m_tuiles.keySet();
        for (Tuile tuile : tuiles) {
            if (tuile.getX() == p_x && tuile.getY() == p_y) {
                return tuile;
            }
        }
        return null;
    }

    /**
     * Permet de vérifier si la tuile ne contient aucun pion
     *
     * @param p_tuile la tuile qu'on veut vérifier si elle est vide
     * @return vrai si la tuile est vide
     * faux si la tuile n'est pas vide
     */
    public boolean estVideTuile(Tuile p_tuile) {
        if (p_tuile.getX() < 0)
            return false;
        if (p_tuile.getY() < 0)
            return false;
        if (p_tuile.getX() > 9)
            return false;
        if (p_tuile.getY() > 9)
            return false;
        return (m_tuiles.get(p_tuile) == null);
    }



    /**
     * Permet d'obtenir les cases de déplacement disponible pour un pion
     *
     * @param p_tuile la tuile d'on on veut obtenir les cases disponibles
     * @return une liste contenant les tuiles où le pion peut se déaplacer
     */
    public LinkedList<Tuile> obtenirCasesDisponibles(Tuile p_tuile) {
        detecterNbMovement(p_tuile, 0);
        Pion.Couleur couleurOpposee;
        if (getCouleurPionSurTuile(p_tuile) == Pion.Couleur.BLANC) {
            couleurOpposee = Pion.Couleur.NOIR;
        } else {
            couleurOpposee = Pion.Couleur.BLANC;
        }
        LinkedList<Tuile> casePossible = new LinkedList<>();
        boolean estBlanc = (getCouleurPionSurTuile(p_tuile) == Pion.Couleur.BLANC);
        boolean estDame = m_tuiles.get(p_tuile) instanceof Dame;

        //Haut Gauche
        if (estDame || estBlanc) {
            boolean aManger = false;

            int i = 0;
            boolean fin = false;


            while (!fin) {
                i++;
                int xtemporaire = p_tuile.getX() - i;
                int yTemporaire = p_tuile.getY() - i;
                if (!estDame) {
                    if (estVideTuile(new Tuile(xtemporaire, yTemporaire))) {
                        if (i == 1)
                            casePossible.add(new Tuile(xtemporaire, yTemporaire));
                    }
                    if (i == 2) {
                        if (!estVideTuile(new Tuile(p_tuile.getX() - 1,
                                p_tuile.getY() - 1)) &&
                                estVideTuile(new Tuile(xtemporaire, yTemporaire))) {
                            if (getCouleurPionSurTuile(new Tuile(p_tuile.getX() - 1,
                                    p_tuile.getY() - 1)) != Pion.Couleur.BLANC) {
                                casePossible.add(new Tuile(xtemporaire, yTemporaire));
                            }
                        }
                        fin = true;
                    }
                } else {
                    if (estVideTuile(new Tuile(xtemporaire, yTemporaire))) {
                        casePossible.add(new Tuile(xtemporaire, yTemporaire));
                        aManger = false;
                    } else if (!aManger && estVideTuile(new Tuile(xtemporaire - 1,
                            yTemporaire - 1))) {
                        if (couleurOpposee ==
                                getCouleurPionSurTuile(new Tuile(xtemporaire, yTemporaire))) {
                            aManger = true;
                        } else {
                            fin = true;
                        }

                    } else {
                        fin = true;
                    }
                }

                if (xtemporaire < 0 || yTemporaire < 0) {
                    fin = true;
                }

            }


        }

        if(!estDame || !estBlanc){
            int xtemporaire = p_tuile.getX() - 2;
            int yTemporaire = p_tuile.getY() - 2;

            if (!estVideTuile(new Tuile(p_tuile.getX() - 1,
                    p_tuile.getY() - 1)) &&
                    estVideTuile(new Tuile(xtemporaire, yTemporaire))) {
                if (getCouleurPionSurTuile(new Tuile(p_tuile.getX() - 1,
                        p_tuile.getY() - 1)) == Pion.Couleur.BLANC) {
                    casePossible.add(new Tuile(xtemporaire, yTemporaire));
                }
            }
        }
        //Haut Droite
        if (estBlanc || estDame) {
            boolean aManger = false;
            int i = 0;
            boolean fin = false;

            while (!fin) {
                i++;
                int xtemporaire = p_tuile.getX() + i;
                int yTemporaire = p_tuile.getY() - i;
                if (!estDame) {
                    if (estVideTuile(new Tuile(xtemporaire, yTemporaire))) {
                        if (i == 1)
                            casePossible.add(new Tuile(xtemporaire, yTemporaire));
                    }
                    if (i == 2) {
                        if (!estVideTuile(new Tuile(p_tuile.getX() + 1,
                                p_tuile.getY() - 1)) && estVideTuile(new Tuile(xtemporaire,
                                yTemporaire))) {
                            if (getCouleurPionSurTuile(new Tuile(p_tuile.getX() + 1,
                                    p_tuile.getY() - 1)) != Pion.Couleur.BLANC) {
                                casePossible.add(new Tuile(xtemporaire, yTemporaire));
                            }
                        }
                        fin = true;
                    }
                } else {
                    if (estVideTuile(new Tuile(xtemporaire, yTemporaire))) {
                        casePossible.add(new Tuile(xtemporaire, yTemporaire));
                        aManger = false;
                    } else if (!aManger && estVideTuile(new Tuile(xtemporaire + 1,
                            yTemporaire - 1))) {
                        if (couleurOpposee == getCouleurPionSurTuile(new Tuile(xtemporaire,
                                yTemporaire))) {
                            aManger = true;
                        } else {
                            fin = true;
                        }
                    } else {
                        fin = true;
                    }
                }
                if (xtemporaire > 9 || yTemporaire < 0) {
                    fin = true;
                }
            }
        }
        if (!estBlanc || estDame){
            int xtemporaire = p_tuile.getX() + 2;
            int yTemporaire = p_tuile.getY() - 2;
            if (!estVideTuile(new Tuile(p_tuile.getX() + 1,
                    p_tuile.getY() - 1)) && estVideTuile(new Tuile(xtemporaire,
                    yTemporaire))) {
                if (getCouleurPionSurTuile(new Tuile(p_tuile.getX() + 1,
                        p_tuile.getY() - 1)) != Pion.Couleur.NOIR) {
                    casePossible.add(new Tuile(xtemporaire, yTemporaire));
                }
            }
        }

            //Bas Droite
        if (estDame || !estBlanc) {
            boolean aManger = false;
            int i = 0;
            boolean fin = false;
            while (!fin) {
                i++;
                int xtemporaire = p_tuile.getX() + i;
                int yTemporaire = p_tuile.getY() + i;
                if (!estDame) {
                    if (estVideTuile(new Tuile(xtemporaire, yTemporaire))) {
                        if (i == 1)
                            casePossible.add(new Tuile(xtemporaire, yTemporaire));
                    }
                    if (i == 2) {
                        if (!estVideTuile(new Tuile(p_tuile.getX() + 1,
                                p_tuile.getY() + 1)) &&
                                estVideTuile(new Tuile(xtemporaire, yTemporaire))) {
                            if (getCouleurPionSurTuile(new Tuile(p_tuile.getX() + 1,
                                    p_tuile.getY() + 1)) != Pion.Couleur.NOIR) {
                                casePossible.add(new Tuile(xtemporaire, yTemporaire));
                            }
                        }
                        fin = true;
                    }
                } else {
                    if (estVideTuile(new Tuile(xtemporaire, yTemporaire))) {
                        casePossible.add(new Tuile(xtemporaire, yTemporaire));
                        aManger = false;
                    } else if (!aManger && estVideTuile(new Tuile(xtemporaire + 1,
                            yTemporaire + 1))) {
                        if (couleurOpposee == getCouleurPionSurTuile(new Tuile(xtemporaire,
                                yTemporaire))) {
                            aManger = true;
                        } else {
                            fin = true;
                        }
                    } else {
                        fin = true;
                    }
                }
                if (xtemporaire > 9 || yTemporaire > 9) {
                    fin = true;
                }
            }
        }
        if(!estDame || estBlanc){
            int xtemporaire = p_tuile.getX() + 2;
            int yTemporaire = p_tuile.getY() + 2;
            if (xtemporaire < 9 && yTemporaire < 9){
                if (!estVideTuile(new Tuile(p_tuile.getX() + 1,
                        p_tuile.getY() + 1)) &&
                        estVideTuile(new Tuile(xtemporaire, yTemporaire))) {
                    if (getCouleurPionSurTuile(new Tuile(p_tuile.getX() + 1,
                            p_tuile.getY() + 1)) != Pion.Couleur.BLANC) {
                        casePossible.add(new Tuile(xtemporaire, yTemporaire));
                    }
                }
            }

        }


        //Bas Gauche
        if (!estBlanc || estDame) {
            boolean aManger = false;
            int i = 0;
            boolean fin = false;
            while (!fin) {
                i++;
                int xtemporaire = p_tuile.getX() - i;
                int yTemporaire = p_tuile.getY() + i;
                if (!estDame) {
                    if (estVideTuile(new Tuile(xtemporaire, yTemporaire))) {
                        if (i == 1)
                            casePossible.add(new Tuile(xtemporaire, yTemporaire));
                    }
                    if (i == 2) {
                        if (!estVideTuile(new Tuile(p_tuile.getX() - 1,
                                p_tuile.getY() + 1)) &&
                                estVideTuile(new Tuile(xtemporaire, yTemporaire))) {
                            if (getCouleurPionSurTuile(new Tuile(p_tuile.getX() - 1,
                                    p_tuile.getY() + 1)) != Pion.Couleur.NOIR) {
                                casePossible.add(new Tuile(xtemporaire, yTemporaire));
                            }
                        }
                        fin = true;
                    }
                } else {
                    if (estVideTuile(new Tuile(xtemporaire, yTemporaire))) {
                        casePossible.add(new Tuile(xtemporaire, yTemporaire));
                        aManger = false;
                    } else if (!aManger && estVideTuile(new Tuile(xtemporaire - 1,
                            yTemporaire + 1))) {
                        if (couleurOpposee == getCouleurPionSurTuile(new Tuile(xtemporaire,
                                yTemporaire))) {
                            aManger = true;
                        } else {
                            fin = true;
                        }
                    } else {
                        fin = true;
                    }
                }

                if (xtemporaire < 0 || yTemporaire > 9) {
                    fin = true;
                }
            }
        }
        if(estBlanc || !estDame){
            int xtemporaire = p_tuile.getX() - 2;
            int yTemporaire = p_tuile.getY() + 2;
            if (!estVideTuile(new Tuile(p_tuile.getX() - 1,
                    p_tuile.getY() + 1)) &&
                    estVideTuile(new Tuile(xtemporaire, yTemporaire))) {
                if (getCouleurPionSurTuile(new Tuile(p_tuile.getX() - 1,
                        p_tuile.getY() + 1)) != Pion.Couleur.BLANC) {
                    casePossible.add(new Tuile(xtemporaire, yTemporaire));
                }
            }
        }

        return casePossible;
    }


    /**
     * Permet de gérer le déplacement lorsque le joueur joue
     *
     * @param p_tuile la tuile initiale
     * @param p_choix la tuile où on veut se déplacer
     */
    public void gererDeplacement(Tuile p_tuile, Tuile p_choix) {

        Pion pion = getPion(p_tuile);

        if (pion == null) {
            throw new IllegalArgumentException();
        }

        if (getPion(p_tuile).getCouleur() == Pion.Couleur.NOIR && p_tuile.getY() >= 8) {
            transformerPion(p_tuile);
        }
        if (getPion(p_tuile).getCouleur() == Pion.Couleur.BLANC && p_tuile.getY() <= 1) {
            transformerPion(p_tuile);
        }

        LinkedList<Tuile> casePossible = obtenirCasesDisponibles(p_tuile);

        Pion.Couleur couleurOpposee;

        if (pion.estBlanc()) {
            couleurOpposee = Pion.Couleur.NOIR;
        } else {
            couleurOpposee = Pion.Couleur.BLANC;
        }

        if (casePossible.contains(p_choix)) {

            if (pion instanceof Dame) {
                // Haut gauche
                if (p_tuile.getY() > p_choix.getY() && p_tuile.getX() > p_choix.getX()) {
                    for (int i = 0; i < p_tuile.getX() - p_choix.getX(); i++) {
                        supprimerPion(new Tuile(p_tuile.getX() - i, p_tuile.getY() - i));

                    }
                    deplacerPion(pion, p_tuile, p_choix);
                }
                //Haut droite
                if (p_tuile.getY() > p_choix.getY() && p_tuile.getX() < p_choix.getX()) {
                    for (int i = 0; i < p_choix.getX() - p_tuile.getX(); i++) {
                        supprimerPion(new Tuile(p_tuile.getX() + i, p_tuile.getY() - i));

                    }
                    deplacerPion(pion, p_tuile, p_choix);
                }
                //Bas gauche
                if (p_tuile.getY() < p_choix.getY() && p_tuile.getX() > p_choix.getX()) {
                    for (int i = 0; i < p_tuile.getX() - p_choix.getX(); i++) {
                        supprimerPion(new Tuile(p_tuile.getX() - i, p_tuile.getY() + i));

                    }
                    deplacerPion(pion, p_tuile, p_choix);
                }
                //Bas Droite
                if (p_tuile.getY() < p_choix.getY() && p_tuile.getX() < p_choix.getX()) {
                    for (int i = 0; i < p_choix.getX() - p_tuile.getX(); i++) {
                        supprimerPion(new Tuile(p_tuile.getX() + i, p_tuile.getY() + i));

                    }
                    deplacerPion(pion, p_tuile, p_choix);
                }
            } else {
                if (pion.estBlanc() ) {
                    if( p_choix.getY() < p_tuile.getY()){
                        if (p_choix.getY() + 2 == p_tuile.getY()) {
                            if (p_choix.getX() + 2 == p_tuile.getX()) {
                                if ((!estVideTuile(new Tuile(p_choix.getX() + 1,
                                        p_choix.getY() + 1)))
                                        && couleurOpposee ==
                                        getCouleurPionSurTuile(new Tuile(p_tuile.getX() - 1,
                                                p_tuile.getY() - 1))) {
                                    supprimerPion(new Tuile(p_tuile.getX() - 1,
                                            p_tuile.getY() - 1));
                                    deplacerPion(pion, p_tuile, p_choix);
                                }
                            } else if (p_choix.getX() - 2 == p_tuile.getX()) {
                                if ((!estVideTuile(new Tuile(p_choix.getX() - 1,
                                        p_choix.getY() + 1)))
                                        && couleurOpposee ==
                                        getCouleurPionSurTuile(new Tuile(p_tuile.getX() + 1,
                                                p_tuile.getY() - 1))) {
                                    supprimerPion(new Tuile(p_tuile.getX() + 1,
                                            p_tuile.getY() - 1));
                                    deplacerPion(pion, p_tuile, p_choix);
                                }
                            }
                        }
                        else {
                            if (estVideTuile(p_choix)) {
                                deplacerPion(pion, p_tuile, p_choix);
                            }
                        }
                    }
                    else if (p_choix.getY() > p_tuile.getY()){
                        if(p_choix.getX()  < p_tuile.getX()){
                            supprimerPion(new Tuile(p_tuile.getX() - 1,
                                    p_tuile.getY() + 1));
                            deplacerPion(pion, p_tuile, p_choix);
                        }
                        if(p_choix.getX() > p_tuile.getX()){
                            supprimerPion(new Tuile(p_tuile.getX() + 1,
                                    p_tuile.getY() + 1));
                            deplacerPion(pion, p_tuile, p_choix);
                        }

                    }

                } else if (pion.estNoir() ) {
                    if(p_choix.getY() > p_tuile.getY()){
                        if (p_choix.getY() - 2 == p_tuile.getY()) {
                            if (p_choix.getX() + 2 == p_tuile.getX()) {
                                if ((!estVideTuile(new Tuile(p_choix.getX() + 1,
                                        p_choix.getY() - 1)))
                                        && couleurOpposee ==
                                        getCouleurPionSurTuile(new Tuile(p_tuile.getX() - 1,
                                                p_tuile.getY() + 1))) {
                                    supprimerPion(new Tuile(p_tuile.getX() - 1,
                                            p_tuile.getY() + 1));
                                    deplacerPion(pion, p_tuile, p_choix);
                                }
                            } else if (p_choix.getX() - 2 == p_tuile.getX()) {
                                if ((!estVideTuile(new Tuile(p_choix.getX() - 1,
                                        p_choix.getY() - 1)))
                                        && couleurOpposee ==
                                        getCouleurPionSurTuile(new Tuile(p_tuile.getX() + 1,
                                                p_tuile.getY() + 1))) {
                                    supprimerPion(new Tuile(p_tuile.getX() + 1,
                                            p_tuile.getY() + 1));
                                    deplacerPion(pion, p_tuile, p_choix);
                                }
                            }
                        } else {
                            if (estVideTuile(p_choix)) {
                                deplacerPion(pion, p_tuile, p_choix);
                            }
                        }
                    }
                    else{
                        if(p_choix.getX()  < p_tuile.getX()){
                            supprimerPion(new Tuile(p_tuile.getX() - 1,
                                    p_tuile.getY() - 1));
                            deplacerPion(pion, p_tuile, p_choix);
                        }
                        if(p_choix.getX() > p_tuile.getX()){
                            supprimerPion(new Tuile(p_tuile.getX() + 1,
                                    p_tuile.getY() - 1));
                            deplacerPion(pion, p_tuile, p_choix);
                        }
                    }

                }
            }
        }

    }

    /**
     * Permet de déterminer la notation manoury d'un déplacement et de l'ajouter dans la liste
     *
     * @param p_tuileActuel   la tuile où le pion est après le déplacement
     * @param p_derniereTuile la tuile où il est rendu
     */
    public void determinerNotationManoury(Tuile p_tuileActuel,
                                          Tuile p_derniereTuile, int p_nbPion) {
        String manoury;

        char signe = '-';

        if (p_nbPion != getNbPions()) {
            signe = 'x';
        }
        if (getCouleurPionSurTuile(p_tuileActuel)
                == Pion.Couleur.BLANC) {
            manoury = "" +
                    transformerPositionEnManoury(p_derniereTuile.getX(),
                            p_derniereTuile.getY()) +
                    signe +
                    transformerPositionEnManoury(p_tuileActuel.getX(),
                            p_tuileActuel.getY());
        } else {
            manoury = "(" +
                    transformerPositionEnManoury(p_derniereTuile.getX(),
                            p_derniereTuile.getY()) +
                    signe +
                    transformerPositionEnManoury(p_tuileActuel.getX(),
                            p_tuileActuel.getY()) + ")";
        }

        m_listeManouryMouvement.add(manoury);

    }

    /**
     * Effectue la supression d'un pion sur une certaine tuile
     *
     * @param p_tuile la tuile où on veut supprimer le pion
     */
    public void supprimerPion(Tuile p_tuile) {
        m_tuiles.put(p_tuile, null);
    }

    /**
     * Permet de vérifier si il reste des pions d'une certaine couleur
     *
     * @param p_couleur la couleur des pions qu'on veut regarder si il en reste
     * @return vrai si il ne reste aucun pion de la couleur
     * faux si il reste encore des pions de la couleur
     */
    public boolean verifierResteAucunPion(Pion.Couleur p_couleur) {
        return (compterCouleur(p_couleur) == 0);
    }

    /**
     * retourne le nombre de mouvement maximal que peut faire un pion sur une tuile
     * @param p_tuile
     * @return
     */
    public int detecterNbMovement(Tuile p_tuile, int p_nbMouvement){
        //Haut gauche
        if (p_tuile != null) {
            if(p_tuile.getTuileHautGauche() != null && p_tuile.getTuileHautGauche().getTuileHautGauche() != null){
                if(!estVideTuile(p_tuile.getTuileHautGauche())){
                    if(estVideTuile(p_tuile.getTuileHautGauche().getTuileHautGauche())){
                        if(getCouleurPionSurTuile(p_tuile) != getCouleurPionSurTuile(p_tuile.getTuileHautGauche())){
                            p_nbMouvement ++;
                            p_nbMouvement = detecterNbMovement(p_tuile.getTuileHautGauche().getTuileHautGauche(), p_nbMouvement);
                        }
                    }
                }
            }

            //Haut Droite
            if(p_tuile.getTuileHautDroite() != null && p_tuile.getTuileHautDroite().getTuileHautDroite() != null){
                if(!estVideTuile(p_tuile.getTuileHautDroite())){
                    if(estVideTuile(p_tuile.getTuileHautDroite().getTuileHautDroite())){
                        if(getCouleurPionSurTuile(p_tuile) != getCouleurPionSurTuile(p_tuile.getTuileHautDroite())){
                            p_nbMouvement ++;
                            p_nbMouvement = detecterNbMovement(p_tuile.getTuileHautDroite().getTuileHautDroite(), p_nbMouvement);
                        }
                    }
                }
            }

            //Bas Droite
            if(p_tuile.getTuileBasDroite() != null && p_tuile.getTuileBasDroite().getTuileBasDroite() != null){
                if(!estVideTuile(p_tuile.getTuileBasDroite())){
                    if(estVideTuile(p_tuile.getTuileBasDroite().getTuileBasDroite())){
                        if(getCouleurPionSurTuile(p_tuile) != getCouleurPionSurTuile(p_tuile.getTuileBasDroite())){
                            p_nbMouvement ++;
                            p_nbMouvement = detecterNbMovement(p_tuile.getTuileBasDroite().getTuileBasDroite(), p_nbMouvement);
                        }
                    }
                }
            }

            //Haut gauche
            if(p_tuile.getTuileBasDroite() != null && p_tuile.getTuileBasDroite().getTuileBasDroite() != null){
                if(!estVideTuile(p_tuile.getTuileBasGauche())){
                    if(estVideTuile(p_tuile.getTuileBasGauche().getTuileBasGauche())){
                        if(getCouleurPionSurTuile(p_tuile) != getCouleurPionSurTuile(p_tuile.getTuileBasGauche())){
                            p_nbMouvement ++;
                            p_nbMouvement = detecterNbMovement(p_tuile.getTuileBasGauche().getTuileBasGauche(), p_nbMouvement);
                        }
                    }
                }
            }

        }

        return p_nbMouvement;
    }

    /**
     * Permet de vérifier si les pions d'une couleur peuvent encore bouger
     *
     * @param p_couleur la couleur des pions qu'on veut regarder si ils peuvent encore bouger
     * @return vrai si aucun pion de la couleur donnée en paramètre peuvent bougé
     */
    public boolean verifierPeuxPasBouger(Pion.Couleur p_couleur) {
        Tuile tuile = new Tuile(0, 0);

        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                tuile.setX(x);
                tuile.setY(y);
                if (m_tuiles.get(tuile) != null) {
                    if (Objects.requireNonNull(m_tuiles.get(tuile)).
                            getCouleur().equals(p_couleur)) {
                        LinkedList<Tuile> caseDispo = obtenirCasesDisponibles(tuile);
                        if (caseDispo.size() != 0) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

}

