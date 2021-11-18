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
        ajouterPion(new Tuile(0,9), new Pion(Pion.Couleur.BLANC));




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



        return null;
    }


    /**
     * Permet de gérer le déplacement lorsque le joueur joue
     *
     * @param p_tuile la tuile initiale
     * @param p_choix la tuile où on veut se déplacer
     */
    public void gererDeplacement(Tuile p_tuile, Tuile p_choix) {
        filtrerListe();
        Pion pion = getPion(p_tuile);
        for (LinkedList<Tuile> liste: listeMove) {
            if(!estVideTuile(p_tuile)){
                if(!(getPion(p_tuile) instanceof Dame )){
                    if(liste.contains(p_tuile) && liste.contains(p_choix)){
                        //Haut Gauche
                        if(p_tuile.getTuileHautGauche() == p_choix){
                            deplacerPion(pion, p_tuile, p_choix);
                        }
                        else if(p_tuile.getTuileHautGauche().getTuileHautGauche() == p_choix){
                            supprimerPion(p_tuile.getTuileHautGauche());
                            deplacerPion(pion, p_tuile, p_choix);
                        }//Haut Droite
                        else if(p_tuile.getTuileHautDroite() == p_choix){
                            deplacerPion(pion, p_tuile, p_choix);
                        }
                        else if(p_tuile.getTuileHautDroite().getTuileHautDroite() == p_choix){
                            supprimerPion(p_tuile.getTuileHautDroite());
                            deplacerPion(pion, p_tuile, p_choix);
                        }//Bas Droite
                        else if(p_tuile.getTuileBasDroite() == p_choix){
                            deplacerPion(pion, p_tuile, p_choix);
                        }
                        else if(p_tuile.getTuileBasDroite().getTuileBasDroite() == p_choix){
                            supprimerPion(p_tuile.getTuileBasDroite());
                            deplacerPion(pion, p_tuile, p_choix);
                        }//Bas Droite
                        else if(p_tuile.getTuileBasGauche() == p_choix){
                            deplacerPion(pion, p_tuile, p_choix);
                        }
                        else if(p_tuile.getTuileBasGauche().getTuileBasGauche() == p_choix){
                            supprimerPion(p_tuile.getTuileBasGauche());
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
        LinkedList<Tuile> listtmp = new LinkedList<Tuile>();
        detecterNbMovementPion(getTuile(4,5), listtmp);
        return (compterCouleur(p_couleur) == 0);

    }

    public LinkedList<LinkedList<Tuile>> listeMove = new LinkedList<LinkedList<Tuile>>();

    /**
     * retourne le nombre de mouvement maximal que peut faire un pion sur une tuile
     * @param p_tuile
     * @return
     */
    public void detecterNbMovementPion(Tuile p_tuile, LinkedList<Tuile> p_mouvementFait){
        //Haut gauche TODO
        if(0 == p_mouvementFait.size()){
            p_mouvementFait.add(p_tuile);
        }
        if (p_tuile != null) {
            boolean Arrete = true;
            if(p_tuile.getTuileHautGauche() != null && p_tuile.getTuileHautGauche().getTuileHautGauche() != null){
                if(!estVideTuile(p_tuile.getTuileHautGauche())){
                    if(estVideTuile(p_tuile.getTuileHautGauche().getTuileHautGauche())){
                        if(getCouleurPionSurTuile(p_tuile) != getCouleurPionSurTuile(p_tuile.getTuileHautGauche())){
                            Arrete = false;
                            LinkedList<Tuile> listtmp = new LinkedList<Tuile>();
                            listtmp.addAll(p_mouvementFait);
                            listtmp.add(p_tuile.getTuileHautGauche().getTuileHautGauche());
                            detecterNbMovementPion(p_tuile.getTuileHautGauche().getTuileHautGauche(), listtmp);
                        }
                    }
                }
            }

            //Haut Droite
            if(p_tuile.getTuileHautDroite() != null && p_tuile.getTuileHautDroite().getTuileHautDroite() != null){
                if(!estVideTuile(p_tuile.getTuileHautDroite())){
                    if(estVideTuile(p_tuile.getTuileHautDroite().getTuileHautDroite())){
                        if(getCouleurPionSurTuile(p_mouvementFait.get(0)) != getCouleurPionSurTuile(p_tuile.getTuileHautDroite())){
                            Arrete = false;
                            LinkedList<Tuile> listtmp = new LinkedList<Tuile>();
                            listtmp.addAll(p_mouvementFait);
                            listtmp.add(p_tuile.getTuileHautDroite().getTuileHautDroite());
                            detecterNbMovementPion(p_tuile.getTuileHautDroite().getTuileHautDroite(), listtmp);
                        }
                    }
                }
            }

            //Bas Droite
            if(p_tuile.getTuileBasDroite() != null && p_tuile.getTuileBasDroite().getTuileBasDroite() != null){
                if(!estVideTuile(p_tuile.getTuileBasDroite())){
                    if(estVideTuile(p_tuile.getTuileBasDroite().getTuileBasDroite())){
                        if(getCouleurPionSurTuile(p_tuile) != getCouleurPionSurTuile(p_tuile.getTuileBasDroite())){
                            Arrete = false;
                            LinkedList<Tuile> listtmp = new LinkedList<Tuile>();
                            listtmp.addAll(p_mouvementFait);
                            listtmp.add(p_tuile.getTuileBasDroite().getTuileBasDroite());
                            detecterNbMovementPion(p_tuile.getTuileBasDroite().getTuileBasDroite(), listtmp);
                        }
                    }
                }
            }

            //Haut gauche
            if(p_tuile.getTuileBasDroite() != null && p_tuile.getTuileBasDroite().getTuileBasDroite() != null){
                if(!estVideTuile(p_tuile.getTuileBasGauche())){
                    if(estVideTuile(p_tuile.getTuileBasGauche().getTuileBasGauche())){
                        if(getCouleurPionSurTuile(p_tuile) != getCouleurPionSurTuile(p_tuile.getTuileBasGauche())){
                            Arrete = false;
                            LinkedList<Tuile> listtmp = new LinkedList<Tuile>();
                            listtmp.addAll(p_mouvementFait);
                            listtmp.add(p_tuile.getTuileBasDroite().getTuileBasDroite());
                            detecterNbMovementPion(p_tuile.getTuileBasDroite().getTuileBasDroite(), listtmp);
                        }
                    }
                }
            }
            if(Arrete){
                if(p_mouvementFait.size() == 1){
                    //Haut Droite
                    if(getCouleurPionSurTuile(p_tuile) == Pion.Couleur.BLANC){
                        if(estVideTuile(p_tuile.getTuileHautDroite())){
                            LinkedList<Tuile> listtmp = new LinkedList<Tuile>();
                            listtmp.add(p_tuile);
                            listtmp.add(p_tuile.getTuileHautDroite());
                            listeMove.add(listtmp);
                        }
                    }
                    //Haut Gauche
                    if(getCouleurPionSurTuile(p_tuile) == Pion.Couleur.BLANC){
                        if(estVideTuile(p_tuile.getTuileHautGauche())){
                            LinkedList<Tuile> listtmp = new LinkedList<Tuile>();
                            listtmp.add(p_tuile);
                            listtmp.add(p_tuile.getTuileHautGauche());
                            listeMove.add(listtmp);
                        }
                    }
                    //Bas Droite
                    if(getCouleurPionSurTuile(p_tuile) == Pion.Couleur.NOIR){
                        if(estVideTuile(p_tuile.getTuileBasDroite())){
                            LinkedList<Tuile> listtmp = new LinkedList<Tuile>();
                            listtmp.add(p_tuile);
                            listtmp.add(p_tuile.getTuileBasDroite());
                            listeMove.add(listtmp);
                        }
                    }
                    //Bas Gauche
                    if(getCouleurPionSurTuile(p_tuile) == Pion.Couleur.NOIR){
                        if(estVideTuile(p_tuile.getTuileBasGauche())){
                            LinkedList<Tuile> listtmp = new LinkedList<Tuile>();
                            listtmp.add(p_tuile);
                            listtmp.add(p_tuile.getTuileBasGauche());
                            listeMove.add(listtmp);
                        }
                    }
                }
                else{
                    listeMove.add(p_mouvementFait);
                }
            }
        }

    }
    public void detecterNbDeplacementDame(Tuile p_tuile, LinkedList<Tuile> p_mouvementFait, Damier p_damier){
        Tuile tuileTmp = p_tuile;
        /* HautGauche */
        while(true){

        }
    }


    public void filtrerListe(){
        int nbMax = 1;
        LinkedList<LinkedList<Tuile>> listTmp = new LinkedList<>();
        for (LinkedList liste:
             listeMove) {
            if(liste.size() > nbMax){
                listTmp.clear();
                listTmp.add(liste);
            }
            else if(liste.size() == nbMax){
                listTmp.add(liste);
            }
        }
        listeMove = listTmp;
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

