
package ohtu.intjoukkosovellus;

class IntJoukko {

    private final static int KAPASITEETTI = 5,      // aloitustalukon koko
                             OLETUSKASVATUS = 5;    // luotava uusi taulukko on näin paljon isompi kuin vanha

    private int kasvatuskoko;   // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] joukko;       // Joukon luvut säilytetään taulukon alkupäässä.
    private int alkioidenLkm;   // Tyhjässä joukossa alkioiden määrä on nolla.

    private static int[] aTaulu;
    private static int[] bTaulu;

    IntJoukko() {
        luoIntJoukko(KAPASITEETTI, OLETUSKASVATUS);
    }

    IntJoukko(int kapasiteetti) {
        luoIntJoukko(kapasiteetti, OLETUSKASVATUS);
    }

    IntJoukko(int kapasiteetti, int kasvatuskoko) {
        luoIntJoukko(kapasiteetti, kasvatuskoko);
    }

    private void luoIntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0 || kasvatuskoko < 0) {
            throw new IndexOutOfBoundsException("Parametrien on oltava positiivisia");
        }
        joukko = new int[kapasiteetti];
        alkioidenLkm = 0;
        this.kasvatuskoko = kasvatuskoko;
    }

    boolean lisaa(int luku) {
        if (!kuuluuJoukkoon(luku)) {
            joukko[alkioidenLkm] = luku;
            alkioidenLkm++;
            kasvataTaulukonKokoa();
            return true;
        }
        return false;
    }

    private void kasvataTaulukonKokoa() {
        if (alkioidenLkm % joukko.length == 0) {
            int[] taulukkoOld;
            taulukkoOld = joukko;
            kopioiTaulukko(joukko, taulukkoOld);
            joukko = new int[alkioidenLkm + kasvatuskoko];
            kopioiTaulukko(taulukkoOld, joukko);
        }
    }


    boolean kuuluuJoukkoon(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == joukko[i]) {
                return true;
            }
        }
        return false;
    }

    boolean poista(int luku) {
        int kohta = -1;
        kohta = etsiLuku(luku, kohta);
        return kohta != -1 && poistaLuku(kohta);
    }

    private boolean poistaLuku(int kohta) {
        int apu;
        for (int j = kohta; j < alkioidenLkm - 1; j++) {
            apu = joukko[j];
            joukko[j] = joukko[j + 1];
            joukko[j + 1] = apu;
        }
        alkioidenLkm--;
        return true;
    }

    private int etsiLuku(int luku, int kohta) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == joukko[i]) {
                kohta = i;
                joukko[kohta] = 0;
                break;
            }
        }
        return kohta;
    }

    private void kopioiTaulukko(int[] vanha, int[] uusi) {
        System.arraycopy(vanha, 0, uusi, 0, vanha.length);

    }

    int mahtavuus() {
        return alkioidenLkm;
    }

    @Override
    public String toString() {
        if (alkioidenLkm == 0) {
            return "{}";
        } else if (alkioidenLkm == 1) {
            return "{" + joukko[0] + "}";
        } else {
            return useidenAlkioidenMerkkijonoesitys();
        }
    }

    private String useidenAlkioidenMerkkijonoesitys() {
        String tuotos = "{";
        for (int i = 0; i < alkioidenLkm - 1; i++) {
            tuotos += joukko[i];
            tuotos += ", ";
        }
        tuotos += joukko[alkioidenLkm - 1];
        tuotos += "}";
        return tuotos;
    }

    int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        System.arraycopy(joukko, 0, taulu, 0, taulu.length);
        return taulu;
    }
   

    static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko yhdisteAB = valmisteleOperaatio(a, b);
        yhdistaJoukot(yhdisteAB, aTaulu, bTaulu);
        return yhdisteAB;
    }

    private static IntJoukko valmisteleOperaatio(IntJoukko a, IntJoukko b) {
        IntJoukko tulosJoukko = new IntJoukko();
        aTaulu = a.toIntArray();
        bTaulu = b.toIntArray();
        return tulosJoukko;
    }

    private static void yhdistaJoukot(IntJoukko yhdisteAB, int[] aTaulu, int[] bTaulu) {
        for (int alkio : aTaulu) {
            yhdisteAB.lisaa(alkio);
        }
        for (int alkio : bTaulu) {
            yhdisteAB.lisaa(alkio);
        }
    }

    static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko leikkausAB = valmisteleOperaatio(a, b);
        muodostaJoukkojenLeikkaus(leikkausAB, aTaulu, bTaulu);
        return leikkausAB;
    }

    private static void muodostaJoukkojenLeikkaus(IntJoukko leikkausAB, int[] aTaulu, int[] bTaulu) {
        for (int alkioA : aTaulu) {
            for (int alkioB : bTaulu) {
                if (alkioA == alkioB) {
                    leikkausAB.lisaa(alkioB);
                }
            }
        }
    }

    static IntJoukko erotus(IntJoukko a, IntJoukko b) {
        IntJoukko erotusAB = valmisteleOperaatio(a, b);
        muodostaJoukkojenErotus(erotusAB, aTaulu, bTaulu);
        return erotusAB;
    }

    private static void muodostaJoukkojenErotus(IntJoukko erotusAB, int[] aTaulu, int[] bTaulu) {
        for (int alkio : aTaulu) {
            erotusAB.lisaa(alkio);
        }
        for (int i = 0; i < bTaulu.length; i++) {
            erotusAB.poista(bTaulu[i]);
        }
    }
}