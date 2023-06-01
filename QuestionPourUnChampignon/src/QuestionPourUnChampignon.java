import extensions.*;

class QuestionPourUnChampignon extends Program{

    final int SIZE = 15 ;
    final double PROBA_QUESTIONS = 0.25;
    int COLJOUEUR = 0;
    int LIGJOUEUR = SIZE/2;
    int IDXQUESTION = 0;
    int champignon = 0;
    String red ="\u001B[31m";
    String white = "\u001B[37m";
    final int NOMBREQUESTION= 22;
    final int TEXT=0, R1=1,R2=2,R3=3,R4=4,REPONSE=5;

    void algorithm() {
        menuChampignon("../ressources/Champi.csv");
        Plateau plateau = new Plateau();
        initialiser(plateau, PROBA_QUESTIONS, SIZE);
        while (champignon != 10) {
            afficherPlateau(plateau);
            deplacement(plateau,"a", LIGJOUEUR, COLJOUEUR);
        }
        println("FELECITATION vous avez fini le jeu");
    }


    // remplit un tableau de question
    void initialiser( Plateau p, double probaQuestions, int size) {
        p.cases = new Case[size][size];
        for (int l = 0; l < size; l++) {
            for (int c = 0; c < size; c++) {
                if ( random() < probaQuestions ) {
                    p.cases[l][c] = Case.QUESTIONS;
                }else{
                    p.cases [l][c] = Case.VIDE;
                }
            }
        }
        p.cases[LIGJOUEUR][COLJOUEUR] = Case.JOUEUR;
    }

    Question menuChampignon(String chemin){
        Question q = new Question();
        CSVFile csv = loadCSV(chemin, ';');
        final int TEXT=0;
        for(int i = 0; i <+ 15; i ++){
            q.text = getCell(csv, i, TEXT);
            println(q.text);
        }
        println(red+"                   QUESTIONS POUR UN CHAMPIGNON"+white+"\n");
        return q;
    }

    void afficherPlateau(Plateau p) {
        Case[][] cases = p.cases;
        for (int l = 0; l < length(cases, 1); l++) {
            print("                 |");
            for (int c = 0; c < length(cases, 2); c++) {
                if (cases[l][c] == Case.JOUEUR) {
                    print(red + "ඞ" + white);
                } else if (cases[l][c] == Case.QUESTIONS) {
                    print("?");
                } else if(cases [l][c] == Case.VIDE){
                    print("*");
                }
                print(" ");
            }
            print("|");
            println("");
        }
    }

    String [][] chargerQuestion(String chemin, int idx){
        Question q = new Question();
        CSVFile csv = loadCSV(chemin, ';');
        String questions [][] = new String[NOMBREQUESTION][REPONSE];
        questions [idx][TEXT] = q.text;
        questions [idx][R1] = getCell(csv, idx, R1);
        questions [idx][R2] = getCell(csv, idx, R2);
        questions [idx][R3] = getCell(csv, idx, R3);
        questions [idx][R4] = getCell(csv, idx, R4);
        questions [idx][REPONSE] = getCell(csv, idx, REPONSE);
        return questions;
    }

    void initialiserQuestion(String chemin,int nombreQuestions){
        String saisie;
        boolean bonneSaisie=false;
        String reponse="";
        Question q = newQuestion();
        while(!q.repondu) {
            String tab [][] = chargerQuestion(chemin, hasard(nombreQuestions));
        }
        println("Question : " + tab [idx][TEXT]);
        println("   1 :"+tab [idx][R1]);
        println("   2 :"+tab [idx][R2]);
        println("   3 :"+ tab [idx][R3]);
        println("   4 :"+tab [idx][R4]);
        println("\n"+"Quelle est votre reponse :");
        while(!bonneSaisie){
            saisie = readString();
            if(equals(saisie,"1")){
                reponse = tab [idx][R1];
                bonneSaisie = true;
            }else if(equals(saisie,"2")){
                reponse = tab [idx][R2];
                bonneSaisie = true;
            }else if(equals(saisie,"3")){
                reponse = tab [idx][R3];
                bonneSaisie = true;
            }else if(equals(saisie,"4")){
                reponse = tab [idx][R4];
                bonneSaisie = true;
            }else {
                print("Veuillez rentrer une saisie valable : ");
                bonneSaisie = false;
            }
        }

        if(equals(reponse,tab [idx][REPONSE])){
            champignon += 1;
            println("Vous avez gagné un champignon ! "+"\uD83C\uDF44");
            q.repondu = true;

        }else{
            println("Mauvaise réponse");
        }
    }

   /* boolean estRepondu(Question q, int idx ){
        idx =0;
        int repondu [] = new int [NOMBREQUESTION];
        if(IDXQUESTION)
        repondu [idx] = IDXQUESTION;
        idx++;
        return true;
    }*/

    int hasard(int nombreQuestion) {
        return (int) (random() * nombreQuestion);
    }

    //fait le déplacmeent est possible est le fait
    void deplacement(Plateau p, String d, int lig, int col){
        Case[][] tab = p.cases;
        boolean bonneSaisie = false;
        println("Vous avez : "+champignon+ " \uD83C\uDF44");
        println("Dans quelle direction voulez vous aller ?(bas=s)(gauche=q)(haut=z)(droite=d)"+"vous avez : "+champignon+" champignon's'");
        while(!bonneSaisie){
            d = readString();
            if(equals(d,"s") && LIGJOUEUR!=SIZE-1){
                tab [LIGJOUEUR][COLJOUEUR] = Case.VIDE;
                lig ++;
                LIGJOUEUR = lig;
                bonneSaisie=true;
            }else if(equals(d,"q") && COLJOUEUR!=0){
                tab [LIGJOUEUR][COLJOUEUR] = Case.VIDE;
                col --;
                COLJOUEUR = col;
                bonneSaisie=true;
            }else if(equals(d,"z") && LIGJOUEUR!=0){
                tab [LIGJOUEUR][COLJOUEUR] = Case.VIDE;
                lig --;
                LIGJOUEUR = lig;
                bonneSaisie=true;
            }else if(equals(d,"d") && COLJOUEUR!=SIZE-1){
                tab [LIGJOUEUR][COLJOUEUR] = Case.VIDE;
                col ++;
                COLJOUEUR = col;
                bonneSaisie=true;
            }else{
                print("Veuillez rentrer une saisie valable : ");
                bonneSaisie = false;
            }
        }
        if(estQuestion(p,LIGJOUEUR,COLJOUEUR)){
            initialiserQuestion("../ressources/Question.csv",NOMBREQUESTION);
        }
        tab [LIGJOUEUR][COLJOUEUR] = Case.JOUEUR;
    }

    boolean estQuestion(Plateau p, int lig, int col){
        Case [][] tab= p.cases;
        if(tab [lig][col] == Case.QUESTIONS){
            return true;
        }else{
            return false;
        }
    }
}