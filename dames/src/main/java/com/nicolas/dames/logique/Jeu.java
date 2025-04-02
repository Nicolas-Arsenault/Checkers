package com.nicolas.dames.logique;

import com.nicolas.dames.controleurs.JouerControleur;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import javafx.scene.image.ImageView;

import java.util.*;

/**
 * @classe Jeu.
 * @visibilite publique (public).
 * @description Représente le jeu de dames et gère le déroulement, ainsi que les validations.
 */

public class Jeu {

    //Référence au controleur de la partie
    private JouerControleur controller;
    //Référence au damier
    private static GridPane damier;
    /** Map pour stocker la position des pions. */
    private static final Map<String, Pion> positionPions = new HashMap<>();
    /** true = tour du joueur, false = tour de l'ennemi. */
    public boolean tourJoueur = true;
    //Variables pour gérer la fin de partie
    public boolean finPartie = false;
    public String gagnant = "";

    /**
     * @constructeur Jeu.
     * @visibilite publique (public).
     * @param damierRef Référence au damier du jeu.
     * @description Constructeur pour initialiser le jeu et avoir une référence au damier.
     */
    public Jeu(GridPane damierRef)
    {
        damier = damierRef;
        initialisationJeu();
    }

    /**
     * @methode initialisationJeu.
     * @visibilite privée (private).
     * @description Méthode pour initialiser les pions sur le plateau.
     */
    public void initialisationJeu()
    {
        // Initialisation des pions du robot ennemi (3 premières lignes)
        initialiserPions(1, 4, true);

        // Initialisation des pions du joueur (3 dernières lignes)
        initialiserPions(6, 9, false);
    }

    public void reintialiserPions()
    {
        positionPions.clear();
    }

    /**
     * @methode initialiserPions.
     * @visibilite privée (private).
     * @description Affiche les informations d'un véhicule à la console.
     * @param debut    La ligne de début, en format int.
     * @param fin      La ligne de fin, en format int.
     * @param estEnnemi Indique si les pions appartiennent au robot, en format boolean.
     * @description Méthode pour initialiser les pions sur une plage de lignes.
     */
    private void initialiserPions(int debut, int fin, boolean estEnnemi)
    {
        for (int i = debut; i < fin; i++) //Boucler dans les lignes (1-8)
        {
            for (int j = 1; j < 9; j++) //Boucler dans les colonnes (A-H).
            {
                if ((i + j) % 2 == 1) //Calcul pour selectionner les cases à mettre les pions dessus.
                {
                    Pion pion = new Pion(estEnnemi); //Créer un nouveau pion et indiquer s'il est un ennemi ou non.
                    positionPions.put(extraireCoordDeXY(i, j), pion); // Ajouter à la "map" le pion créé, ainsi que sa position sur le plateau (i, j).
                }
            }
        }
    }


    /**
     * @methode extraireCoordDeXY.
     * @visibilite privée (private).
     * @description Méthode pour obtenir la coordonnée en chaîne à partir des indices XY.
     * @param x L'indice X (lignes 1-8), en format int.
     * @param y L'indice Y (colonnes A-H), en format int.
     * @return La coordonnée en chaîne, en format String.
     */
    public static String extraireCoordDeXY(int x, int y)
    {
        return String.valueOf((char) (y + '@')) + (x); //y + ASCII de @ = lettre du plateau (colonne). X = Chiffre du plateau (ligne).
    }

    /**
     * @methode updateAllImages.
     * @visibilite publique (public).
     * @description Méthode pour metter à jour toutes les images sur le plateau, selon la position des pions.
     */

    public void updateAllImages() {

        // Supprimer toutes les images
        damier.getChildren().removeIf(node -> node instanceof ImageView);

        // Redessiner touets les image selon leur position
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                String coord = extraireCoordDeXY(i, j);
                Pion pion = positionPions.get(coord);
                boolean estCarreVert = (i + j) % 2 == 1;

                //Dessiner juste sur les carrés verts
                if (pion != null && estCarreVert) {
                    String imageSource;

                    if (pion.estDame) {
                        imageSource = pion.estEnnemi ? "/images/noir-dame.png" : "/images/blanc-dame.png";
                    } else {
                        imageSource = pion.estEnnemi ? "/images/noir-normal.png" : "/images/blanc-normal.png";
                    }

                    // Créer l'image View
                    Image image = new Image(Objects.requireNonNull(Jeu.class.getResourceAsStream(imageSource)));
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(35);
                    imageView.setFitHeight(35);
                    imageView.setPreserveRatio(true);

                    //Positionner l'image
                    GridPane.setRowIndex(imageView, i);
                    GridPane.setColumnIndex(imageView, j);

                    // Ajouter une translation
                    imageView.setTranslateX(10);
                    imageView.setTranslateY(0);

                    // Ajouter au damier
                    damier.getChildren().add(imageView);
                }
            }
        }
    }

    /**
     * @methode dessinerJeu.
     * @visibilite publique (public).
     * @description  Méthode pour dessiner le plateau de jeu.
     */

    public void dessinerJeu()
    {
        for(int i=1; i < 9; i ++)
        {
            for(int j = 1; j < 9; j++)
            {
                String coord = extraireCoordDeXY(i,j);
                Pion pion = positionPions.get(coord);

                String imagePath = "";
                boolean estCarreVert = (i + j) % 2 == 1;



                if (pion != null && estCarreVert) {  // S'il y a un pion et que c'est un carrée vert
                    if (pion.estDame) {
                        imagePath = pion.estEnnemi ? "/images/noir-dame.png" : "/images/blanc-dame.png"; // Dame noire ou blanche
                    } else {
                        imagePath = pion.estEnnemi ? "/images/noir-normal.png" : "/images/blanc-normal.png"; // Pion noir ou blanc
                    }
                }

                //Créer la Vue de l'image
                Image image = new Image(Objects.requireNonNull(Jeu.class.getResourceAsStream(imagePath)));
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(35);
                imageView.setFitHeight(35);
                imageView.setPreserveRatio(true);

                //Positionner la vue
                GridPane.setRowIndex(imageView, i);
                GridPane.setColumnIndex(imageView, j);

                //Ajouter l'image au damier
                damier.getChildren().add(imageView);

                //Ajouter une translation à la vue
                imageView.setTranslateX(10);
                imageView.setTranslateY(0);
            }
        }
    }



    /**
     * @methode saisieValide.
     * @visibilite privée (private).
     * @description Vérifier si la saisie de la case est valide.
     * @param saisie La saisie de l'utilisateur, en format String.
     * @return true si la saisie est valide, false sinon, en format boolean.
     */
    private boolean saisieValide(String saisie)
    {
        if (saisie.length() != 2) return false; //Vérifie que l'utilisateur a saisi deux caractères.
        return saisie.matches("^[A-H][1-8]$"); //Vérifie si l'utilisateur a effectué une saisie dans la plage (A1-H8).
    }


    /**
     * @methode extraireValeurX.
     * @visibilite privée (private).
     * @description Extraire la position X de la saisie.
     * @param saisie La saisie de l'utilisateur, en format String.
     * @return La position X, en format int.
     */
    private int extraireValeurX(String saisie)
    {
        return (saisie.charAt(1) - '0'); //Retourne l'indice X (ligne)... 49 = code ASCII pour '1'.
    }

    /**
     *
     * @methode extraireValeurY.
     * @visibilite privée (private).
     * @description Extraire la position Y de la saisie.
     * @param saisie La saisie de l'utilisateur, en format String.
     * @return La position Y. en format int.
     */
    private int extraireValeurY(String saisie)
    {
        return (saisie.charAt(0) - 'A' + 1); //Retourne l'indice Y (colonne)... 65 = ASCII de 'A'.
    }

    /**
     * @methode extractionPion.
     * @visibilite privée (private).
     * @description Extraire un pion en fonction de sa position.
     * @param saisie    La saisie de l'utilisateur, en format String.
     * @param estEnnemi Indique si le pion appartient au robot, en format boolean.
     * @return Le pion extrait, ou null si non trouvé, en format Pion.
     */

    public Pion extractionPion(String saisie, boolean estEnnemi)
    {
        String coord = saisie.toUpperCase(); //Convertir la saisie en majuscule (si ce n'est pas déja le cas).
        Pion tempoPion = positionPions.get(coord); //Extraire le pion à la coordonnée saisie.

        if (tempoPion != null && tempoPion.estEnnemi != estEnnemi) //Si le pion existe et qu'il ne correspond pas à sa valeur estEnnemi, retourner null.
        {
            return null;
        }
        return tempoPion;
    }

    /**
     * @methode estDeplacementValide.
     * @visibilite privée (private).
     * @description Méthode pour vérifier si le déplacement est valide.
     * @param coordActuelle   La coordonnée actuelle du pion, en format String.
     * @param coordDeplacement La coordonnée de déplacement, en format String.
     * @return true si le déplacement est valide, false sinon, en format boolean.
     */
    public boolean estDeplacementValide(String coordActuelle, String coordDeplacement) {
        // Calculer les coordonnées actuelles et de destination.
        int xActuel = extraireValeurX(coordActuelle);
        int yActuel = extraireValeurY(coordActuelle);

        int destinationX = extraireValeurX(coordDeplacement);
        int destinationY = extraireValeurY(coordDeplacement);

        // Calculer la différence en X et Y.
        int deltaX = destinationX - xActuel;
        int deltaY = destinationY - yActuel;

        // Vérifier si le déplacement est diagonal.
        if (Math.abs(deltaX) != Math.abs(deltaY)) {
            return false;
        }

        //Vérifier si le déplacement est trop grand
        if(Math.abs(deltaX) > 2 || Math.abs(deltaY) > 2)
        {
            return false;
        }

        // Vérifier si on essaie de reculer dépendamment du pion.
        Pion pionActuel = positionPions.get(coordActuelle);
        boolean estEnnemi = pionActuel.estEnnemi; // True si c'est un pion ennemi, false sinon.

        // Si c'est un pion joueur, il ne doit pas reculer (pour les pions non-dames).
        if (!estEnnemi && !pionActuel.estDame) {
            if (destinationX > xActuel) { // Si le pion va vers le bas (vers les lignes plus grandes), c'est un recul.
                return false;
            }
        }

        // Si c'est un pion ennemi, il ne doit pas reculer non plus (pour les pions non-dames).
        if (estEnnemi && !pionActuel.estDame) {
            if (destinationX < xActuel) { // Si le pion va vers le haut (vers les lignes plus petites), c'est un recul.
                return false;
            }
        }

        // Vérifier si la case de destination est vide.
        Pion destinationPion = positionPions.get(coordDeplacement);

        if (destinationPion != null) {
            return false; // La case de destination est occupée, le déplacement n'est pas valide.
        }

        // Vérifier si le déplacement est une prise (déplacement de deux cases).
        if (Math.abs(deltaX) == 2 && Math.abs(deltaY) == 2) {
            // Calculer les coordonnées de la case intermédiaire.
            int intermediaireX = xActuel + deltaX / 2;
            int intermediaireY = yActuel + deltaY / 2;

            String coordIntermediaire = extraireCoordDeXY(intermediaireX, intermediaireY);
            Pion pionIntermediaire = positionPions.get(coordIntermediaire);

            // Vérifier si la case intermédiaire contient un pion ennemi.
            return pionIntermediaire != null && pionIntermediaire.estEnnemi != pionActuel.estEnnemi; // La case intermédiaire ne contient pas un pion ennemi.

            // La prise est valide.
        }

        // Si le déplacement est d'une case, vérifier qu'il n'y a pas de pion à la destination.
        return true;
    }

    /**
     * @methode verifPromotionDame.
     * @visibilite privée (private).
     * @description Méthode pour vérifier si un pion doit être promu en dame.
     * @param coordonnee La coordonnée du pion, en format String.
     */
    private void verifPromotionDame(String coordonnee)
    {
        Pion tempoPion = positionPions.get(coordonnee); //Extraire le pion à la coordonnée spécifiée.
        
        if(tempoPion.estEnnemi && extraireValeurX(coordonnee) == 8) //Si c'est un ennemi et qu'il se trouve à l'autre bout du plateau, faire la promotion.
        {
            tempoPion.estDame = true;
        } else if(!tempoPion.estEnnemi && extraireValeurX(coordonnee) == 1) //Si c'est l'un de nos pions et qu'il se trouve à l'autre bout...
        {
            tempoPion.estDame = true;
        }
    }

    /**
     * @methode deplacerImageView.
     * @visibilite privée (private).
     * @description Méthode pour déplacer les Vues d'images.
     * @param imageView Référence à la vue de l'image à déplacer
     * @param destinationY Coordonnée en Y du déplacement de l'image
     * @param destinationX  Coordonnée en X du déplacement de l'image
     */

    private void deplacerImageView(ImageView imageView, int destinationX, int destinationY) {
        // Vérification si l'ImageView existe toujours dans le GridPane
        if (imageView != null) {

            GridPane.setRowIndex(imageView, destinationX);
            GridPane.setColumnIndex(imageView, destinationY);

            JouerControleur.estSelectionnee = false;
        }
    }


    /**
     * @methode setController.
     * @visibilite publique (public)
     * @description Méthode pour avoir une référence au controleur du jeu
     */
    public void setController(JouerControleur controller) {
        this.controller = controller;
    }

    /**
     * @methode mettreAJourPositionPions.
     * @visibilite privée (private).
     * @description Méthode pour mettre à jour la position des pions.
     * @param coordActuelle   La coordonnée actuelle du pion, en format String.
     * @param coordDeplacement La coordonnée de déplacement, en format String.
     * @param pion            Le pion à déplacer, en Pion.
     */
    public void mettreAJourPositionPions(String coordActuelle, String coordDeplacement, Pion pion) {
        // Calculer les coordonnées actuelles et de destination
        int xActuel = extraireValeurX(coordActuelle);
        int yActuel = extraireValeurY(coordActuelle);

        int destinationX = extraireValeurX(coordDeplacement);
        int destinationY = extraireValeurY(coordDeplacement);

        // Calculer la différence en X et Y
        int deltaX = destinationX - xActuel;
        int deltaY = destinationY - yActuel;

        // Vérifier si le déplacement est une prise (déplacement de deux cases)
        if (Math.abs(deltaX) == 2 && Math.abs(deltaY) == 2) {
            // Calculer les coordonnées de la case intermédiaire (où se trouve le pion adverse)
            int intermediaireX = xActuel + deltaX / 2;
            int intermediaireY = yActuel + deltaY / 2;

            String coordIntermediaire = extraireCoordDeXY(intermediaireX, intermediaireY);
            Pion pionIntermediaire = positionPions.get(coordIntermediaire);

            // Vérifier si la case intermédiaire contient un pion ennemi
            if (pionIntermediaire != null && pionIntermediaire.estEnnemi != pion.estEnnemi) {
                // Vérifier que la case de destination est vide
                Pion destinationPion = positionPions.get(coordDeplacement);
                if (destinationPion == null) {

                    if(positionPions.get(coordIntermediaire).estEnnemi)
                    {
                        //Incrémenter la capture du pion mangé
                        controller.incrementerCaptureNoir(positionPions.get(coordIntermediaire).estDame); //Incrémenter la capture du pion mangé

                    }
                    else
                    {
                        //Incrémenter la capture du pion mangé
                        controller.incrementerCaptureBlanc(positionPions.get(coordIntermediaire).estDame);//Incrémenter la capture du pion mangé
                    }
                    // Retirer le pion ennemi mangé (case intermédiaire)
                    positionPions.remove(coordIntermediaire);

                    // Déplacer notre pion sur la case de destination
                    positionPions.put(coordDeplacement, pion);

                    // Retirer le pion de sa position actuelle
                    positionPions.remove(coordActuelle);

                    // Vérifier la promotion en dame
                    verifPromotionDame(coordDeplacement);

                    // Déplacer l'ImageView à la nouvelle position dans le GridPane
                    for (Node node : damier.getChildren()) {
                        Integer rowIndex = GridPane.getRowIndex(node);
                        Integer colIndex = GridPane.getColumnIndex(node);

                        if (node instanceof ImageView imageView && rowIndex != null && colIndex != null
                                && rowIndex == xActuel && colIndex == yActuel) {

                            // Utilisation de la fonction pour déplacer l'ImageView
                            deplacerImageView(imageView, destinationX, destinationY);

                            break;  // Exit the loop once the ImageView has been moved
                        }
                    }
                }
            }
        } else {
            // Si la destination est libre, simplement déplacer le pion
            Pion destinationPion = positionPions.get(coordDeplacement);
            if (destinationPion == null) {
                positionPions.put(coordDeplacement, pion);

                // Retirer le pion de sa position actuelle
                positionPions.remove(coordActuelle);

                // Vérifier la promotion en dame
                verifPromotionDame(coordDeplacement);

                // Déplacer l'ImageView à la nouvelle position dans le GridPane
                for (Node node : damier.getChildren()) {
                    Integer rowIndex = GridPane.getRowIndex(node);
                    Integer colIndex = GridPane.getColumnIndex(node);

                    if (node instanceof ImageView imageView && rowIndex != null && colIndex != null
                            && rowIndex == xActuel && colIndex == yActuel) {

                        // Utilisation de la fonction pour déplacer l'ImageView
                        deplacerImageView(imageView, destinationX, destinationY);

                        break;  // Exit the loop once the ImageView has been moved
                    }
                }
            }
        }
    }

    /**
     * @methode cancellerSelection.
     * @visibilite publique (public).
     * @description Méthode pour canceller une sélection d'un pion.
     * @param x Cordonnée de l'image à déselectionner
     * @param y Cordonnée de l'image à déselectionner
     */

    public void cancellerSelection(int x, int y) {
        //Extraire le Pion concerné
        String coord = extraireCoordDeXY(x, y);
        Pion pion = positionPions.get(coord);

        //Extraire l'image non selectionnée du pion
        if (pion != null) {
            String imagePath;
            if (pion.estDame) {
                imagePath = pion.estEnnemi ? "/images/noir-dame.png" : "/images/blanc-dame.png";
            } else {
                imagePath = pion.estEnnemi ? "/images/noir-normal.png" : "/images/blanc-normal.png";
            }
            //Mettre à jour cette image
            updateImageView(x, y, imagePath);
        }
    }

    /**
     * @methode effectuerSelection.
     * @visibilite publique (public).
     * @description Méthode pour changer l'image d'un pion à celui d'un pion selectionné
     * @param x Cordonnée du pion à sélectionner
     * @param y Cordonnée du pion à sélectionner
     */

    public void effectuerSelection(int x, int y) {

        //Extraire le pion concerné
        String coord = extraireCoordDeXY(x, y);
        Pion pion = positionPions.get(coord);

        //Extraire l'image sélectionné du pion
        if (pion != null) {
            String imagePath;
            if (pion.estDame) {
                imagePath = pion.estEnnemi ? "/images/noir-dame-selection.png" : "/images/blanc-dame-selection.png";
            } else {
                imagePath = pion.estEnnemi ? "/images/noir-normal-selection.png" : "/images/blanc-normal-selection.png";
            }
            //Mettre à jour l'image
            updateImageView(x, y, imagePath);
        }
    }

    /**
     * @methode updateImageView.
     * @visibilite privée (private).
     * @description Méthode pour mettre à jour un pion unique
     * @param x Cordonnée du pion à changer
     * @param y Cordonnée du pion à changer
     * @param imagePath Emplacement de l'image à lui assigner
     */

    private void updateImageView(int x, int y, String imagePath) {

        //Chercher dans le damier la cordonnée x,y
        for (Node node : damier.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer colIndex = GridPane.getColumnIndex(node);

            if (rowIndex != null && colIndex != null &&
                    rowIndex == x && colIndex == y &&
                    node instanceof ImageView imageView) {

                //Mettre à jour l'image

                //Préserver les propriétés de l'image actuelle
                Image image = new Image(Objects.requireNonNull(Jeu.class.getResourceAsStream(imagePath)));
                imageView.setImage(image);
                break;
            }
        }
    }

    /**
     * @methode tourDeLEnemi.
     * @visibilite publique (public).
     * @description Méthode pour gérer le tour de l'ennemi.
     * @return true si le tour est terminé, false sinon, en format boolean.
     */
    public boolean tourDeLEnemi() {
        /*
            Ordre de priorité du IA:
            1. dame capture
            2. capture normale
            3. deplacement dame
            4. deplacement normal
         */

        // Extraire tous les pions ennemis
        List<String> pionsEnnemi = new ArrayList<>();
        for (Map.Entry<String, Pion> entree : positionPions.entrySet()) {
            if (entree.getValue().estEnnemi) {
                pionsEnnemi.add(entree.getKey());
            }
        }

        //Si aucun pions n'est disponible, terminer la partie.
        if (pionsEnnemi.isEmpty()) {

            estFinDePartie();
            return false;
        }

        // Vérifier les captures possibles, priorisant les dames
        List<String> captureSources = new ArrayList<>();
        List<String> captureDestinations = new ArrayList<>();
        List<Boolean> estCaptureDame = new ArrayList<>();

        //Vérifier tous les captures possibles de chaques pions
        for (String pionChoisi : pionsEnnemi) {
            Pion pion = positionPions.get(pionChoisi);
            int x = extraireValeurX(pionChoisi);
            int y = extraireValeurY(pionChoisi);

            //Vérifier si un pion peut capturer un autre
            int[][] captureDirections = pion.estDame ?
                    new int[][]{{2,2}, {2,-2}, {-2,2}, {-2,-2}} : // Mouvements dames
                    new int[][]{{2,2}, {2,-2}}; // Mouvement pions

            //Vérifier si c'est valide...
            for (int[] dir : captureDirections)
            {
                String destination = extraireCoordDeXY(x + dir[0], y + dir[1]);

                if (saisieValide(destination) && estDeplacementValide(pionChoisi, destination))
                {
                    //Si c'est valide, l'ajouter dans une liste.
                    captureSources.add(pionChoisi);
                    captureDestinations.add(destination);
                    estCaptureDame.add(pion.estDame);
                }
            }
        }

        // Si une capture est possible, prioriser une capture de dame
        if (!captureSources.isEmpty()) {
            // Essayer de trouver une capture de dame en premier
            for (int i = 0; i < captureSources.size(); i++) {
                if (estCaptureDame.get(i)) {
                    //Effectuer la capture
                    mettreAJourPositionPions(captureSources.get(i), captureDestinations.get(i),
                            positionPions.get(captureSources.get(i)));
                    dessinerJeu();
                    return true;
                }
            }
            //Si aucunes captures de dames, essayer une capture ordinaire
            int randomIndex = new Random().nextInt(captureSources.size());
            //Mettre à jour la capture
            mettreAJourPositionPions(captureSources.get(randomIndex), captureDestinations.get(randomIndex),
                    positionPions.get(captureSources.get(randomIndex)));
            dessinerJeu();
            return true;
        }

        //Mouvements normaux, (priorité aux dames)
        List<String> damePions = new ArrayList<>();
        List<String> normalPions = new ArrayList<>();

        //Ajouter le type de pion dans une liste
        for (String coord : pionsEnnemi) {
            if (positionPions.get(coord).estDame) {
                damePions.add(coord);
            } else {
                normalPions.add(coord);
            }
        }

        // Essayer la dame en premier
        if (!damePions.isEmpty()) {
            Collections.shuffle(damePions);
            for (String pionChoisi : damePions) {
                Pion pion = positionPions.get(pionChoisi);
                int x = extraireValeurX(pionChoisi);
                int y = extraireValeurY(pionChoisi);

                // La dame peut bouger partout
                int[][] moveDirections = {{1,1}, {1,-1}, {-1,1}, {-1,-1}};
                List<String> destinations = new ArrayList<>();

                for (int[] dir : moveDirections) {
                    String destination = extraireCoordDeXY(x + dir[0], y + dir[1]);
                    if (saisieValide(destination) && estDeplacementValide(pionChoisi, destination)) {
                        destinations.add(destination);
                    }
                }

                if (!destinations.isEmpty()) {
                    String destinationChoisie = destinations.get(new Random().nextInt(destinations.size()));
                    //Faire le déplacement au hasard
                    mettreAJourPositionPions(pionChoisi, destinationChoisie, pion);
                    dessinerJeu();
                    return true;
                }
            }
        }

        // Essayer avec les pions normaux
        if (!normalPions.isEmpty()) {
            Collections.shuffle(normalPions);
            for (String pionChoisi : normalPions) {
                Pion pion = positionPions.get(pionChoisi);
                int x = extraireValeurX(pionChoisi);
                int y = extraireValeurY(pionChoisi);

                // Ils peuvent juste avancer
                int[][] moveDirections = {{1,1}, {1,-1}};
                List<String> destinations = new ArrayList<>();

                for (int[] dir : moveDirections) {
                    String destination = extraireCoordDeXY(x + dir[0], y + dir[1]);
                    if (saisieValide(destination) && estDeplacementValide(pionChoisi, destination)) {
                        destinations.add(destination);
                    }
                }

                if (!destinations.isEmpty()) {
                    String destinationChoisie = destinations.get(new Random().nextInt(destinations.size()));
                    //Faire le déplacement au hasard
                    mettreAJourPositionPions(pionChoisi, destinationChoisie, pion);
                    dessinerJeu();
                    return true;
                }
            }
        }

        //Sinon, tous les pions sont bloqués
        finirPartie();
        return false;
    }


    /**
     * @methode changerTour.
     * @visibilite publique (public).
     * @description Méthode pour changer le tour (joueur ou ennemi).
     */
    public void changerTour()
    {
        tourJoueur = !tourJoueur;
    }

    /**
     * @methode finirPartie.
     * @visibilite privée (private).
     * @description Méthode pour terminer la partie.
     */
    private void finirPartie()
    {
        finPartie =true;
        gagnant = "blancs";
    }

    /**
     * @methode getGagnant.
     * @visibilite publique (public).
     * @description Méthode "Getter" qui retourne le gagnant de la partie.
     * @return String, le gagnant de la partie
     */
    public String getGagnant() {
        return gagnant;
    }


    /**
     * @methode reinitialiserJeu.
     * @visibilite publique (public).
     * @description Méthode pour réinitialiser l'entièreté du jeu.
     */

    public void reinitialiserJeu() {
        finPartie = false;
        gagnant = "";
        reintialiserPions();
        initialisationJeu();
    }

    /**
     * @methode estFinDePartie.
     * @visibilite publique (public).
     * @description Méthode pour vérifier si la partie est terminée.
     * @return true si la partie est terminée, false sinon, en format boolean.
     */
    public boolean estFinDePartie() {
        if (finPartie) return true; // Already finished

        List<Pion> pEnnemi = new ArrayList<>();
        List<Pion> pJoueur = new ArrayList<>();

        for (Map.Entry<String, Pion> entree : positionPions.entrySet()) {
            Pion tempoPion = entree.getValue();
            if (tempoPion.estEnnemi) {
                pEnnemi.add(tempoPion);
            } else {
                pJoueur.add(tempoPion);
            }
        }

        if (pEnnemi.isEmpty()) {
            finPartie = true;
            gagnant = "blancs";
            return true;
        } else if (pJoueur.isEmpty()) {
            finPartie = true;
            gagnant = "noirs";
            return true;
        }

        return false;
    }
}
