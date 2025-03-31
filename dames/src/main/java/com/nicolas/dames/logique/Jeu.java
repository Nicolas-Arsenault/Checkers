package com.nicolas.dames.logique;

import com.nicolas.dames.controleurs.JouerControleur;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import javafx.scene.image.ImageView;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @classe Jeu.
 * @visibilite publique (public).
 * @description Représente le jeu de dames et gère le déroulement, ainsi que les validations.
 */

public class Jeu {

    // Constantes pour les couleurs dans la console
    private static GridPane damier;
    /** Map pour stocker la position des pions. */
    private static final Map<String, Pion> positionPions = new HashMap<>();
    /** true = tour du joueur, false = tour de l'ennemi. */
    public boolean tourJoueur = true;
    public boolean finPartie = false;
    public String gagnant = "";

    /**
     * @constructeur Jeu.
     * @visibilite publique (public).
     * @description Constructeur pour initialiser le jeu.
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
        //coord ok
        return String.valueOf((char) (y + '@')) + (x); //y + ASCII de A = lettre du plateau (colonne). X + ASCII de 1 = Chiffre du plateau (ligne).

    }

    public void updateAllImages() {
        // Step 1: Delete all existing images
        damier.getChildren().removeIf(node -> node instanceof ImageView);

        // Step 2: Redraw all images based on current game state
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                String coord = extraireCoordDeXY(i, j);
                Pion pion = positionPions.get(coord);
                boolean isBlackSquare = (i + j) % 2 == 1;

                // Only draw on black squares and when there's a pawn
                if (pion != null && isBlackSquare) {
                    String imagePath = "";

                    if (pion.estDame) {
                        imagePath = pion.estEnnemi ? "/images/noir-dame.png" : "/images/blanc-dame.png";
                    } else {
                        imagePath = pion.estEnnemi ? "/images/noir-normal.png" : "/images/blanc-normal.png";
                    }

                    // Create and configure the new image
                    Image image = new Image(Jeu.class.getResourceAsStream(imagePath));
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(35);
                    imageView.setFitHeight(35);
                    imageView.setPreserveRatio(true);

                    // Position the image in the grid
                    GridPane.setRowIndex(imageView, i);
                    GridPane.setColumnIndex(imageView, j);

                    // Add slight translation if needed
                    imageView.setTranslateX(10);
                    imageView.setTranslateY(0);

                    // Add to grid
                    damier.getChildren().add(imageView);
                }
            }
        }
    }

    /**
     * @methode dessinerJeu.
     * @visibilite publique (public).
     * @description  Méthiode pour dessiner le plateau de jeu.
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
                boolean isBlackSquare = (i + j) % 2 == 1;



                if (pion != null && isBlackSquare) {  // If there's a pawn and it's a black square
                    if (pion.estDame) {
                        imagePath = pion.estEnnemi ? "/images/noir-dame.png" : "/images/blanc-dame.png"; // Black or white queen
                    } else {
                        imagePath = pion.estEnnemi ? "/images/noir-normal.png" : "/images/blanc-normal.png"; // Black or white pawn
                    }
                }

// Add the image to the grid
                Image image = new Image(Jeu.class.getResourceAsStream(imagePath));
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(35);  // Adjust this size as necessary
                imageView.setFitHeight(35);
                imageView.setPreserveRatio(true);

// Set correct positioning
                GridPane.setRowIndex(imageView, i);
                GridPane.setColumnIndex(imageView, j);

// Add ImageView to GridPane
                damier.getChildren().add(imageView);

// Optional: To ensure it's correctly clickable, no need to use TranslateX/TranslateY unless needed
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
            if (pionIntermediaire == null || pionIntermediaire.estEnnemi == pionActuel.estEnnemi) {
                return false; // La case intermédiaire ne contient pas un pion ennemi.
            }

            // La prise est valide.
            return true;
        }

        // Si le déplacement est d'une case, vérifier qu'il n'y a pas de pion à la destination.
        return true;
    }

    /**
     *
     * @methode verifPromotionDame.
     * @visibilite privée (private).
     * @description Méthode pour vérifier si un pion doit être promu en dame.
     * @param coordonnee La coordonnée du pion, en format String.
     */
    private void verifPromotionDame(String coordonnee)
    {
        System.out.println(coordonnee);
        Pion tempoPion = positionPions.get(coordonnee); //Extraire le pion à la coordonnée spécifiée.
        
        if(tempoPion.estEnnemi && extraireValeurX(coordonnee) == 8) //Si c'est un ennemi et qu'il se trouve à l'autre bout du plateau, faire la promotion.
        {
            tempoPion.estDame = true;
        } else if(!tempoPion.estEnnemi && extraireValeurX(coordonnee) == 1) //Si c'est l'un de nos pions et qu'il se trouve à l'autre bout...
        {
            tempoPion.estDame = true;
        }
    }


    public void deplacerImageView(ImageView imageView,int xActuel, int yActuel, int destinationX, int destinationY) {
        // Vérification si l'ImageView existe toujours dans le GridPane
        if (imageView != null) {

            GridPane.setRowIndex(imageView, destinationX);
            GridPane.setColumnIndex(imageView, destinationY);

            JouerControleur.estSelectionnee = false;
            System.out.println("Déplacement effectué à la position (" + destinationX + ", " + destinationY + ")" + " à partir de " + xActuel + " " + yActuel);
        } else {
            System.out.println("L'ImageView est null, impossible de déplacer.");
        }
    }
    private JouerControleur controller;

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
                        if(positionPions.get(coordIntermediaire).estDame)
                        {
                            controller.incrementerCaptureNoir(true);
                        }
                        else
                        {
                            controller.incrementerCaptureNoir(false);
                        }

                    }
                    else
                    {
                        if(positionPions.get(coordIntermediaire).estDame)
                        {
                            controller.incrementerCaptureBlanc(true);
                        }
                        else
                        {
                            controller.incrementerCaptureBlanc(false);
                        }
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

                        if (node instanceof ImageView && rowIndex != null && colIndex != null
                                && rowIndex == xActuel && colIndex == yActuel) {
                            ImageView imageView = (ImageView) node;

                            // Utilisation de la fonction pour déplacer l'ImageView
                            deplacerImageView(imageView, xActuel, yActuel, destinationX, destinationY);

                            break;  // Exit the loop once the ImageView has been moved
                        }
                    }


                    // Afficher le déplacement
                    afficherDeplacement(coordActuelle, coordDeplacement, pion.estEnnemi);
                } else {
                    System.out.println("La case de destination n'est pas vide. Déplacement annulé.");
                }
            } else {
                System.out.println("Aucun pion ennemi à manger. Déplacement annulé.");
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

                    if (node instanceof ImageView && rowIndex != null && colIndex != null
                            && rowIndex == xActuel && colIndex == yActuel) {
                        ImageView imageView = (ImageView) node;

                        // Utilisation de la fonction pour déplacer l'ImageView
                        deplacerImageView(imageView, xActuel, yActuel, destinationX, destinationY);

                        break;  // Exit the loop once the ImageView has been moved
                    }
                }

                // Afficher le déplacement
                afficherDeplacement(coordActuelle, coordDeplacement, pion.estEnnemi);
            } else {
                System.out.println("La case de destination est occupée. Déplacement annulé.");
            }
        }
    }


    /**
     * @methode afficherDeplacement.
     * @visibilite privée (private)
     * @description Méthode pour afficher le déplacement d'un pion.
     * @param coordActuelle   La coordonnée actuelle du pion, en format String.
     * @param coordDeplacement La coordonnée de déplacement, en format String.
     * @param estEnnemi        Indique si le pion appartient au robot, en format boolean.
     */
    private void afficherDeplacement(String coordActuelle, String coordDeplacement, boolean estEnnemi)
    {
        System.out.println( "Déplacement du pion " + coordActuelle + " à la case " + coordDeplacement + "."); //Écrire le déplacement.
    }

//NOTE changer le traitement tour par tour... utiliser une variable bool pour désigner c'est le tour à qui et faire les traitements selon les cliques.
    //garder le tour de l'IA

    public void cancellerSelection(int x, int y) {
        String coord = extraireCoordDeXY(x, y);
        Pion pion = positionPions.get(coord);

        if (pion != null) {
            String imagePath;
            if (pion.estDame) {
                imagePath = pion.estEnnemi ? "/images/noir-dame.png" : "/images/blanc-dame.png";
            } else {
                imagePath = pion.estEnnemi ? "/images/noir-normal.png" : "/images/blanc-normal.png";
            }
            updateImageView(x, y, imagePath);
        }
    }

    public void effectuerSelection(int x, int y) {
        String coord = extraireCoordDeXY(x, y);
        Pion pion = positionPions.get(coord);

        if (pion != null) {
            String imagePath;
            if (pion.estDame) {
                imagePath = pion.estEnnemi ? "/images/noir-dame-selection.png" : "/images/blanc-dame-selection.png";
            } else {
                imagePath = pion.estEnnemi ? "/images/noir-normal-selection.png" : "/images/blanc-normal-selection.png";
            }
            updateImageView(x, y, imagePath);
        }
    }

    private void updateImageView(int x, int y, String imagePath) {
        for (Node node : damier.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer colIndex = GridPane.getColumnIndex(node);

            if (rowIndex != null && colIndex != null &&
                    rowIndex == x && colIndex == y &&
                    node instanceof ImageView) {

                ImageView imageView = (ImageView) node;
                // Preserve all existing properties and listeners
                Image image = new Image(Jeu.class.getResourceAsStream(imagePath));
                imageView.setImage(image);
                break;
            }
        }
    }

    /**
     * @methode aucunMouvementPossible.
     * @visibilite privée (private).
     * @description Méthode pour vérifier si un joueur (joueur ou ennemi) ne peut plus effectuer de mouvements valides.
     * @param estEnnemi Indique si on vérifie les mouvements pour l'ennemi (true) ou pour le joueur (false), en format boolean.
     * @return true si aucun mouvement n'est possible, false sinon, en format boolean.
     */
    private boolean aucunMouvementPossible(boolean estEnnemi)
    {
        // Parcourir tous les pions du joueur ou de l'ennemi.
        for (Map.Entry<String, Pion> entree : positionPions.entrySet())
        {
            String coordPion = entree.getKey(); // Coordonnée du pion.
            Pion pion = entree.getValue(); // Pion à cette coordonnée.

            // Vérifier si le pion appartient au joueur ou à l'ennemi en fonction du paramètre estEnnemi.
            if (pion.estEnnemi == estEnnemi)
            {
                // Vérifier les déplacements possibles pour ce pion.
                for (int x = -1; x <= 1; x++) // Déplacement vertical.
                {
                    for (int y = -1; y <= 1; y++) // Déplacement horizontal.
                    {

                        if (Math.abs(x) == Math.abs(y) && x != 0 && y != 0) // Vérifier si le déplacement est diagonal (différence absolue égale) et non nul.
                        {
                            // Calculer la destination potentielle.
                            String destination = extraireCoordDeXY(extraireValeurX(coordPion) + x, extraireValeurY(coordPion) + y);


                            // Vérifier si la destination est valide et si le déplacement est possible.
                            if (saisieValide(destination) && estDeplacementValide(coordPion, destination))
                            {
                                return false; // Un mouvement valide a été trouvé.
                            }
                        }
                    }
                }
            }
        }

        // Aucun mouvement valide n'a été trouvé pour ce joueur.
        return true;
    }

    /**
     * @methode tourDeLEnemi.
     * @visibilite publique (public).
     * @description Méthode pour gérer le tour de l'ennemi.
     * @return true si le tour est terminé, false sinon, en format boolean.
     */
    public boolean tourDeLEnemi() {
        // Get all enemy pawns
        List<String> pionsEnnemi = new ArrayList<>();
        for (Map.Entry<String, Pion> entree : positionPions.entrySet()) {
            if (entree.getValue().estEnnemi) {
                pionsEnnemi.add(entree.getKey());
            }
        }

        if (pionsEnnemi.isEmpty()) {

            estFinDePartie();
            System.out.println("Aucun pion ennemi disponible. Tour terminé.");
            return false;
        }

        // 1. First check for possible captures, prioritizing queens
        List<String> captureSources = new ArrayList<>();
        List<String> captureDestinations = new ArrayList<>();
        List<Boolean> isQueenCaptures = new ArrayList<>();

        for (String pionChoisi : pionsEnnemi) {
            Pion pion = positionPions.get(pionChoisi);
            int x = extraireValeurX(pionChoisi);
            int y = extraireValeurY(pionChoisi);

            // Check capture directions (queens can capture in all directions)
            int[][] captureDirections = pion.estDame ?
                    new int[][]{{2,2}, {2,-2}, {-2,2}, {-2,-2}} : // Queen directions
                    new int[][]{{2,2}, {2,-2}}; // Regular pawn directions (forward only)

            for (int[] dir : captureDirections) {
                String destination = extraireCoordDeXY(x + dir[0], y + dir[1]);
                if (saisieValide(destination) && estDeplacementValide(pionChoisi, destination)) {
                    captureSources.add(pionChoisi);
                    captureDestinations.add(destination);
                    isQueenCaptures.add(pion.estDame);
                }
            }
        }

        // If captures available, prioritize queen captures
        if (!captureSources.isEmpty()) {
            // Try to find queen captures first
            for (int i = 0; i < captureSources.size(); i++) {
                if (isQueenCaptures.get(i)) {
                    mettreAJourPositionPions(captureSources.get(i), captureDestinations.get(i),
                            positionPions.get(captureSources.get(i)));
                    dessinerJeu();
                    return true;
                }
            }
            // If no queen captures, do regular capture
            int randomIndex = new Random().nextInt(captureSources.size());
            mettreAJourPositionPions(captureSources.get(randomIndex), captureDestinations.get(randomIndex),
                    positionPions.get(captureSources.get(randomIndex)));
            dessinerJeu();
            return true;
        }

        // 2. Normal movement (prioritizing queen moves)
        List<String> queenPawns = new ArrayList<>();
        List<String> regularPawns = new ArrayList<>();

        for (String coord : pionsEnnemi) {
            if (positionPions.get(coord).estDame) {
                queenPawns.add(coord);
            } else {
                regularPawns.add(coord);
            }
        }

        // Try queens first
        if (!queenPawns.isEmpty()) {
            Collections.shuffle(queenPawns);
            for (String pionChoisi : queenPawns) {
                Pion pion = positionPions.get(pionChoisi);
                int x = extraireValeurX(pionChoisi);
                int y = extraireValeurY(pionChoisi);

                // Queen can move in all 4 directions
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
                    mettreAJourPositionPions(pionChoisi, destinationChoisie, pion);
                    dessinerJeu();
                    return true;
                }
            }
        }

        // Then try regular pawns
        if (!regularPawns.isEmpty()) {
            Collections.shuffle(regularPawns);
            for (String pionChoisi : regularPawns) {
                Pion pion = positionPions.get(pionChoisi);
                int x = extraireValeurX(pionChoisi);
                int y = extraireValeurY(pionChoisi);

                // Regular pawns can only move forward
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
                    mettreAJourPositionPions(pionChoisi, destinationChoisie, pion);
                    dessinerJeu();
                    return true;
                }
            }
        }

        System.out.println("Tous les pions ennemis sont bloqués. La partie est terminée.");
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
    public String getGagnant() {
        return gagnant;
    }

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
