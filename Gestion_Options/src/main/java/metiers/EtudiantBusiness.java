
package metiers;

import entities.Option;
import entities.Etudiant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EtudiantBusiness {

    private static List<Etudiant> etudiants;

    private OptionBusiness optionBusiness = new OptionBusiness();

    public EtudiantBusiness() {

        if (etudiants == null) {

            etudiants = new ArrayList<Etudiant>();

            // Initialisation avec quelques étudiants de test
            etudiants.add(new Etudiant(
                    "I001",
                    "Doe",
                    "Jean",
                    optionBusiness.getOptionByCode(1),
                    2023,
                    "jean.doe@example.com"
            ));

            etudiants.add(new Etudiant(
                    "I002",
                    "Smith",
                    "Alice",
                    optionBusiness.getOptionByCode(1),
                    2022,
                    "alice.smith@example.com"
            ));

            etudiants.add(new Etudiant(
                    "I003",
                    "Durand",
                    "Pierre",
                    optionBusiness.getOptionByCode(2),
                    2023,
                    "pierre.durand@example.com"
            ));
        }
    }

    // B1 : Ajouter un étudiant
    public boolean addEtudiant(Etudiant etudiant) {

        if (etudiant == null || etudiant.getOption() == null) {
            return false;
        }

        // Vérifier si l'étudiant existe déjà
        if (getEtudiantByIdentifiant(etudiant.getIdentifiant()) != null) {
            return false;
        }

        // Vérifier si l'option existe
        int codeOption = etudiant.getOption().getCodeOption();

        Option option = optionBusiness.getOptionByCode(codeOption);

        if (option != null) {
            etudiant.setOption(option);
            return etudiants.add(etudiant);
        }

        return false;
    }

    // B2 : Récupérer tous les étudiants
    public List<Etudiant> getAllEtudiants() {
        return etudiants;
    }

    // B3 : Récupérer un étudiant par son identifiant
    public Etudiant getEtudiantByIdentifiant(String identifiant) {

        if (identifiant == null) {
            return null;
        }

        for (Etudiant e : etudiants) {

            if (e.getIdentifiant().equals(identifiant)) {
                return e;
            }
        }

        return null;
    }

    // B4 : Supprimer un étudiant
    public boolean deleteEtudiant(String identifiant) {

        if (identifiant == null) {
            return false;
        }

        Iterator<Etudiant> iterator = etudiants.iterator();

        while (iterator.hasNext()) {

            Etudiant e = iterator.next();

            if (e.getIdentifiant().equals(identifiant)) {
                iterator.remove();
                return true;
            }
        }

        return false;
    }

    // B5 : Modifier un étudiant
    public boolean updateEtudiant(
            String identifiant,
            Etudiant updatedEtudiant) {

        if (identifiant == null || updatedEtudiant == null) {
            return false;
        }

        if (updatedEtudiant.getOption() == null) {
            return false;
        }

        // Vérifier si l'étudiant existe
        Etudiant ancienEtudiant = getEtudiantByIdentifiant(identifiant);

        if (ancienEtudiant == null) {
            return false;
        }

        // Vérifier si l'option existe
        int codeOption = updatedEtudiant.getOption().getCodeOption();

        Option option = optionBusiness.getOptionByCode(codeOption);

        if (option == null) {
            return false;
        }

        // Conserver l'identifiant de l'URL
        updatedEtudiant.setIdentifiant(identifiant);

        // Utiliser l'objet Option existant
        updatedEtudiant.setOption(option);

        // Remplacer l'ancien étudiant
        int index = etudiants.indexOf(ancienEtudiant);

        etudiants.set(index, updatedEtudiant);

        return true;
    }

    // B6 : Récupérer les étudiants par code d'option
    public List<Etudiant> getEtudiantsByOption(int codeOption) {

        List<Etudiant> result = new ArrayList<Etudiant>();

        for (Etudiant e : etudiants) {

            if (e.getOption() != null
                    && e.getOption().getCodeOption() == codeOption) {

                result.add(e);
            }
        }

        return result;
    }
}

