package com.example.schoollife;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class QuizManager {
    private static QuizManager instance;
    private Map<String, ArrayList<Question>> quizzes;

    private QuizManager() {
        quizzes = new HashMap<>();
        initializeQuizzes();
    }

    public static QuizManager getInstance() {
        if (instance == null) {
            instance = new QuizManager();
        }
        return instance;
    }

    private void initializeQuizzes() {
        // Physics Questions
        ArrayList<Question> physicsQuestions = new ArrayList<>();

        physicsQuestions.add(new Question(
                "What is the SI unit of electric current?",
                "Volt",
                "Ampere",
                "Ohm",
                "Coulomb",
                2)); // Increased by 1

        physicsQuestions.add(new Question(
                "Who proposed the laws of motion?",
                "Albert Einstein",
                "Galileo Galilei",
                "Isaac Newton",
                "Nikola Tesla",
                3)); // Increased by 1

        physicsQuestions.add(new Question(
                "What does a voltmeter measure?",
                "Current",
                "Resistance",
                "Voltage",
                "Power",
                3)); // Increased by 1

        physicsQuestions.add(new Question(
                "Which particle has a negative charge?",
                "Proton",
                "Electron",
                "Neutron",
                "Photon",
                2)); // Increased by 1

        physicsQuestions.add(new Question(
                "What is the speed of light in vacuum?",
                "3 × 10^6 m/s",
                "3 × 10^7 m/s",
                "3 × 10^8 m/s",
                "3 × 10^9 m/s",
                3)); // Increased by 1

        physicsQuestions.add(new Question(
                "Which law states that energy cannot be created or destroyed?",
                "Ohm's Law",
                "Newton's First Law",
                "Law of Conservation of Energy",
                "Hooke's Law",
                3)); // Increased by 1

        physicsQuestions.add(new Question(
                "Which of these is a scalar quantity?",
                "Velocity",
                "Acceleration",
                "Force",
                "Temperature",
                4)); // Increased by 1

        physicsQuestions.add(new Question(
                "What does Ohm's Law relate?",
                "Current, voltage, and resistance",
                "Force, mass, and acceleration",
                "Power, current, and voltage",
                "Energy, mass, and light",
                1)); // Increased by 1

        physicsQuestions.add(new Question(
                "Which of the following is not a fundamental force?",
                "Gravitational force",
                "Electromagnetic force",
                "Frictional force",
                "Nuclear force",
                3)); // Increased by 1

        physicsQuestions.add(new Question(
                "What is the unit of frequency?",
                "Joule",
                "Watt",
                "Hertz",
                "Newton",
                3)); // Increased by 1

        physicsQuestions.add(new Question(
                "Which device converts electrical energy into mechanical energy?",
                "Battery",
                "Generator",
                "Motor",
                "Transformer",
                3)); // Increased by 1

        physicsQuestions.add(new Question(
                "What is measured in Newtons?",
                "Energy",
                "Mass",
                "Force",
                "Work",
                3)); // Increased by 1

        physicsQuestions.add(new Question(
                "Which wave does not require a medium to travel?",
                "Sound wave",
                "Water wave",
                "Seismic wave",
                "Light wave",
                4)); // Increased by 1

        physicsQuestions.add(new Question(
                "What is kinetic energy?",
                "Energy stored in an object",
                "Energy due to motion",
                "Energy of light",
                "Thermal energy",
                2)); // Increased by 1

        physicsQuestions.add(new Question(
                "What is the acceleration due to gravity on Earth?",
                "8.9 m/s²",
                "9.8 m/s²",
                "10.8 m/s²",
                "11.2 m/s²",
                2)); // Increased by 1
        quizzes.put("Physics", physicsQuestions);

        // Chemistry Questions
        ArrayList<Question> chemistryQuestions = new ArrayList<>();

        chemistryQuestions.add(new Question(
                "What is the chemical symbol for gold?",
                "Au",
                "Ag",
                "Gd",
                "Go",
                1)); // Increased by 1

        chemistryQuestions.add(new Question(
                "What is the pH value of pure water?",
                "0",
                "7",
                "14",
                "1",
                2)); // Increased by 1

        chemistryQuestions.add(new Question(
                "Which gas is most abundant in the Earth's atmosphere?",
                "Oxygen",
                "Carbon Dioxide",
                "Nitrogen",
                "Hydrogen",
                3)); // Increased by 1

        chemistryQuestions.add(new Question(
                "What is the atomic number of carbon?",
                "6",
                "12",
                "8",
                "4",
                1)); // Increased by 1

        chemistryQuestions.add(new Question(
                "Which element is a noble gas?",
                "Nitrogen",
                "Chlorine",
                "Argon",
                "Hydrogen",
                3)); // Increased by 1

        chemistryQuestions.add(new Question(
                "What type of bond involves the sharing of electron pairs?",
                "Ionic bond",
                "Covalent bond",
                "Metallic bond",
                "Hydrogen bond",
                2)); // Increased by 1

        chemistryQuestions.add(new Question(
                "H2O is the chemical formula for what substance?",
                "Hydrogen peroxide",
                "Hydroxide",
                "Water",
                "Oxygen gas",
                3)); // Increased by 1

        chemistryQuestions.add(new Question(
                "Which acid is found in the human stomach?",
                "Sulfuric acid",
                "Acetic acid",
                "Hydrochloric acid",
                "Nitric acid",
                3)); // Increased by 1

        chemistryQuestions.add(new Question(
                "Which of these is an alkali metal?",
                "Calcium",
                "Sodium",
                "Aluminum",
                "Iron",
                2)); // Increased by 1

        chemistryQuestions.add(new Question(
                "What is the chemical symbol for sodium?",
                "Na",
                "So",
                "Sn",
                "Sm",
                1)); // Increased by 1

        chemistryQuestions.add(new Question(
                "Which compound is commonly known as table salt?",
                "NaCl",
                "KCl",
                "Na2CO3",
                "CaCl2",
                1)); // Increased by 1

        chemistryQuestions.add(new Question(
                "What is the process of a solid changing directly into a gas called?",
                "Evaporation",
                "Condensation",
                "Sublimation",
                "Melting",
                3)); // Increased by 1

        chemistryQuestions.add(new Question(
                "Which element has the highest electronegativity?",
                "Oxygen",
                "Fluorine",
                "Nitrogen",
                "Chlorine",
                2)); // Increased by 1

        chemistryQuestions.add(new Question(
                "Which state of matter has a definite volume but no definite shape?",
                "Solid",
                "Liquid",
                "Gas",
                "Plasma",
                2)); // Increased by 1

        chemistryQuestions.add(new Question(
                "What is the main component of natural gas?",
                "Butane",
                "Methane",
                "Propane",
                "Ethane",
                2)); // Increased by 1

        quizzes.put("Chemistry", chemistryQuestions);

        // Mathematics Questions
        ArrayList<Question> mathsQuestions = new ArrayList<>();
        mathsQuestions.add(new Question(
                "What is the value of π (pi) rounded to two decimal places?",
                "3.12",
                "3.14",
                "3.16",
                "3.18",
                2)); // Increased by 1

        mathsQuestions.add(new Question(
                "What is 7 × 8?",
                "54",
                "56",
                "58",
                "64",
                2)); // Increased by 1

        mathsQuestions.add(new Question(
                "What is the square root of 144?",
                "11",
                "12",
                "13",
                "14",
                2)); // Increased by 1

        mathsQuestions.add(new Question(
                "Which number is a prime number?",
                "4",
                "6",
                "7",
                "9",
                3)); // Increased by 1

        mathsQuestions.add(new Question(
                "What is the formula for the area of a circle?",
                "πr²",
                "2πr",
                "πd",
                "r²",
                1)); // Increased by 1

        mathsQuestions.add(new Question(
                "What is 25% of 200?",
                "25",
                "40",
                "50",
                "75",
                3)); // Increased by 1

        mathsQuestions.add(new Question(
                "If x + 3 = 10, what is x?",
                "5",
                "6",
                "7",
                "8",
                3)); // Increased by 1

        mathsQuestions.add(new Question(
                "How many degrees are in a right angle?",
                "45°",
                "90°",
                "180°",
                "360°",
                2)); // Increased by 1

        mathsQuestions.add(new Question(
                "What is the perimeter of a square with side length 6 cm?",
                "12 cm",
                "18 cm",
                "24 cm",
                "36 cm",
                3)); // Increased by 1

        mathsQuestions.add(new Question(
                "What is the next number in the sequence: 2, 4, 8, 16, ___?",
                "18",
                "20",
                "24",
                "32",
                4)); // Increased by 1

        mathsQuestions.add(new Question(
                "What is the value of 3² + 4²?",
                "25",
                "20",
                "12",
                "5",
                1)); // Increased by 1

        mathsQuestions.add(new Question(
                "Which shape has exactly 3 sides?",
                "Square",
                "Rectangle",
                "Triangle",
                "Circle",
                3)); // Increased by 1

        mathsQuestions.add(new Question(
                "What is the median of this data set: 3, 7, 9, 10, 11?",
                "7",
                "9",
                "10",
                "11",
                2)); // Increased by 1

        mathsQuestions.add(new Question(
                "What is 0.75 as a fraction?",
                "3/4",
                "1/2",
                "2/5",
                "4/5",
                1)); // Increased by 1

        mathsQuestions.add(new Question(
                "If a triangle has angles of 90° and 30°, what is the third angle?",
                "30°",
                "45°",
                "60°",
                "90°",
                3)); // Increased by 1

        quizzes.put("Maths", mathsQuestions);

        // Geography Questions
        ArrayList<Question> geographyQuestions = new ArrayList<>();
        geographyQuestions.add(new Question(
                "What is the largest continent by area?",
                "Africa",
                "Asia",
                "North America",
                "Europe",
                2)); // Increased by 1

        geographyQuestions.add(new Question(
                "Which is the longest river in the world?",
                "Amazon River",
                "Yangtze River",
                "Nile River",
                "Mississippi River",
                3)); // Increased by 1

        geographyQuestions.add(new Question(
                "What is the capital city of France?",
                "Berlin",
                "Madrid",
                "Rome",
                "Paris",
                4)); // Increased by 1

        geographyQuestions.add(new Question(
                "Which ocean is the largest?",
                "Atlantic Ocean",
                "Indian Ocean",
                "Pacific Ocean",
                "Arctic Ocean",
                3)); // Increased by 1

        geographyQuestions.add(new Question(
                "Mount Everest is located in which mountain range?",
                "Andes",
                "Rocky Mountains",
                "Alps",
                "Himalayas",
                4)); // Increased by 1

        geographyQuestions.add(new Question(
                "Which country has the most population?",
                "United States",
                "India",
                "China",
                "Russia",
                3)); // Increased by 1

        geographyQuestions.add(new Question(
                "What is the capital of Japan?",
                "Seoul",
                "Beijing",
                "Tokyo",
                "Bangkok",
                3)); // Increased by 1

        geographyQuestions.add(new Question(
                "Which desert is the largest in the world?",
                "Sahara Desert",
                "Gobi Desert",
                "Kalahari Desert",
                "Arctic Desert",
                1)); // Increased by 1

        geographyQuestions.add(new Question(
                "Which country is known as the Land of the Rising Sun?",
                "Thailand",
                "China",
                "Japan",
                "Vietnam",
                3)); // Increased by 1

        geographyQuestions.add(new Question(
                "What is the smallest country in the world?",
                "Monaco",
                "Vatican City",
                "San Marino",
                "Liechtenstein",
                2)); // Increased by 1

        geographyQuestions.add(new Question(
                "Which continent is the Sahara Desert located in?",
                "Asia",
                "Africa",
                "Australia",
                "South America",
                2)); // Increased by 1

        geographyQuestions.add(new Question(
                "Which river flows through Baghdad?",
                "Euphrates",
                "Jordan",
                "Tigris",
                "Danube",
                3)); // Increased by 1

        geographyQuestions.add(new Question(
                "Which two continents are located entirely in the Southern Hemisphere?",
                "Asia and Africa",
                "Australia and Antarctica",
                "South America and Asia",
                "Europe and South America",
                2)); // Increased by 1

        geographyQuestions.add(new Question(
                "Which U.S. state has the most coastline?",
                "California",
                "Florida",
                "Alaska",
                "Hawaii",
                3)); // Increased by 1

        geographyQuestions.add(new Question(
                "Which is the deepest ocean trench in the world?",
                "Puerto Rico Trench",
                "Java Trench",
                "Tonga Trench",
                "Mariana Trench",
                4)); // Increased by 1

        quizzes.put("Geography", geographyQuestions);

        // Biology Questions
        ArrayList<Question> biologyQuestions = new ArrayList<>();
        biologyQuestions.add(new Question(
                "What is the basic unit of life?",
                "Atom",
                "Molecule",
                "Cell",
                "Organ",
                3)); // Increased by 1

        biologyQuestions.add(new Question(
                "Which organ pumps blood throughout the body?",
                "Lungs",
                "Brain",
                "Liver",
                "Heart",
                4)); // Increased by 1

        biologyQuestions.add(new Question(
                "What is the process by which plants make their food?",
                "Respiration",
                "Photosynthesis",
                "Transpiration",
                "Fermentation",
                2)); // Increased by 1

        biologyQuestions.add(new Question(
                "Which part of the cell contains genetic material?",
                "Cytoplasm",
                "Mitochondria",
                "Nucleus",
                "Ribosome",
                3)); // Increased by 1

        biologyQuestions.add(new Question(
                "How many chambers does the human heart have?",
                "2",
                "3",
                "4",
                "5",
                3)); // Increased by 1

        biologyQuestions.add(new Question(
                "Which gas do humans exhale?",
                "Oxygen",
                "Carbon Dioxide",
                "Nitrogen",
                "Hydrogen",
                2)); // Increased by 1

        biologyQuestions.add(new Question(
                "What is the largest organ in the human body?",
                "Heart",
                "Liver",
                "Skin",
                "Brain",
                3)); // Increased by 1

        biologyQuestions.add(new Question(
                "Which blood cells fight infection?",
                "Red blood cells",
                "White blood cells",
                "Platelets",
                "Plasma",
                2)); // Increased by 1

        biologyQuestions.add(new Question(
                "Which vitamin is produced when the skin is exposed to sunlight?",
                "Vitamin A",
                "Vitamin B",
                "Vitamin C",
                "Vitamin D",
                4)); // Increased by 1

        biologyQuestions.add(new Question(
                "Which organ is responsible for removing waste from the blood?",
                "Lungs",
                "Kidneys",
                "Stomach",
                "Pancreas",
                2)); // Increased by 1

        biologyQuestions.add(new Question(
                "What is the scientific name for humans?",
                "Homo erectus",
                "Homo sapiens",
                "Homo habilis",
                "Homo neanderthalensis",
                2)); // Increased by 1

        biologyQuestions.add(new Question(
                "What do herbivores eat?",
                "Meat",
                "Plants",
                "Both plants and meat",
                "Insects",
                2)); // Increased by 1

        biologyQuestions.add(new Question(
                "Which part of the plant conducts photosynthesis?",
                "Root",
                "Stem",
                "Leaf",
                "Flower",
                3)); // Increased by 1

        biologyQuestions.add(new Question(
                "What type of reproduction involves only one parent?",
                "Sexual",
                "Asexual",
                "External fertilization",
                "Internal fertilization",
                2)); // Increased by 1

        biologyQuestions.add(new Question(
                "Which system controls body activities with hormones?",
                "Nervous system",
                "Circulatory system",
                "Digestive system",
                "Endocrine system",
                4)); // Increased by 1

        quizzes.put("Biology", biologyQuestions);
    }


    public ArrayList<Question> getShuffledQuestions(String quizType) {
        // Ստանում ենք բնօրինակ հարցերի ցուցակը
        ArrayList<Question> originalQuestions = quizzes.get(quizType);
        if (originalQuestions == null) {
            return null;
        }

        // Ստեղծում ենք նոր ցուցակ խառնված հարցերի համար
        ArrayList<Question> shuffledQuestions = new ArrayList<>();

        // Անցնում ենք բոլոր հարցերի վրայով և ստեղծում ենք նոր օրինակներ
        for (Question original : originalQuestions) {
            // Վերցնում ենք բոլոր տարբերակները զանգվածի մեջ
            String[] options = {
                    original.getOption1(),
                    original.getOption2(),
                    original.getOption3(),
                    original.getOption4()
            };

            // Հիշում ենք ճիշտ պատասխանի տեքստը
            String correctAnswer = options[original.getCorrectAnswer() - 1]; // Փոփոխված է այստեղ

            // Ստեղծում ենք տարբերակների ArrayList և խառնում այն
            ArrayList<String> shuffledOptions = new ArrayList<>();
            for (String option : options) {
                shuffledOptions.add(option);
            }
            Collections.shuffle(shuffledOptions);

            // Գտնում ենք ճիշտ պատասխանի նոր ինդեքսը խառնված տարբերակներում
            int newCorrectIndex = -1;
            for (int i = 0; i < shuffledOptions.size(); i++) { // Փոփոխված է այստեղ
                if (shuffledOptions.get(i).equals(correctAnswer)) {
                    newCorrectIndex = i;
                    break;
                }
            }

            // Ստուգում ենք, որ ճիշտ ինդեքսը գտնվել է
            if (newCorrectIndex == -1) {
                System.err.println("Error: Correct answer not found after shuffling for question: " + original.getQuestion());
                // Հետ ենք վերադարձնում օրիգինալ հարցը առանց խառնման
                shuffledQuestions.add(new Question(
                        original.getQuestion(),
                        original.getOption1(),
                        original.getOption2(),
                        original.getOption3(),
                        original.getOption4(),
                        original.getCorrectAnswer()
                ));
            } else {
                // Ստեղծում ենք նոր հարց խառնված տարբերակներով
                shuffledQuestions.add(new Question(
                        original.getQuestion(),
                        shuffledOptions.get(0),
                        shuffledOptions.get(1),
                        shuffledOptions.get(2),
                        shuffledOptions.get(3),
                        newCorrectIndex + 1 // Փոփոխված է այստեղ
                ));
            }
        }

        // Խառնում ենք հարցերի հերթականությունը
        Collections.shuffle(shuffledQuestions);

        return shuffledQuestions;
    }

    public ArrayList<Question> getQuestions(String quizType) {
        // Վերադարձնում ենք խառնված հարցերի ցուցակը
        return getShuffledQuestions(quizType);
    }

    public ArrayList<String> getQuizTypes() {
        ArrayList<String> types = new ArrayList<>(quizzes.keySet());
        return types;
    }
}