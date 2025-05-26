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

        physicsQuestions.add(new Question("What does Ohm's Law relate?",
                "Current, voltage, and resistance",
                "Force, mass, and acceleration",
                "Power, current, and voltage",
                "Energy, mass, and light",
                1));

        physicsQuestions.add(new Question("What is the unit of force?",
                "Joule",
                "Watt",
                "Newton",
                "Pascal",
                3));

        physicsQuestions.add(new Question("Which particle has a negative charge?",
                "Proton",
                "Electron",
                "Neutron",
                "Photon",
                2));

        physicsQuestions.add(new Question("What does the first law of thermodynamics state?",
                "Energy cannot be created or destroyed",
                "Force equals mass times acceleration",
                "For every action, there is an equal reaction",
                "The entropy of the universe is constant",
                1));

        physicsQuestions.add(new Question("What is the acceleration due to gravity on Earth?",
                "6.8 m/s²",
                "9.8 m/s²",
                "10.8 m/s²",
                "1.6 m/s²",
                2));

// 5 down, 40 to go...
        physicsQuestions.add(new Question("Which law explains why rockets work?",
                "Newton’s First Law",
                "Newton’s Second Law",
                "Newton’s Third Law",
                "Law of Conservation of Energy",
                3));

        physicsQuestions.add(new Question("What does a volt measure?",
                "Current",
                "Resistance",
                "Power",
                "Electric potential difference",
                4));

        physicsQuestions.add(new Question("What is the speed of light in a vacuum?",
                "3.0 × 10⁸ m/s",
                "1.5 × 10⁸ m/s",
                "9.8 × 10⁸ m/s",
                "3.0 × 10⁶ m/s",
                1));

        physicsQuestions.add(new Question("Which wave has the highest frequency?",
                "Microwaves",
                "Radio waves",
                "Gamma rays",
                "Infrared",
                3));

        physicsQuestions.add(new Question("What is the SI unit of power?",
                "Joule",
                "Watt",
                "Volt",
                "Ampere",
                2));

        physicsQuestions.add(new Question("Which scientist developed the laws of motion?",
                "Einstein",
                "Newton",
                "Galileo",
                "Tesla",
                2));

        physicsQuestions.add(new Question("What type of lens converges light rays?",
                "Concave",
                "Convex",
                "Flat",
                "None",
                2));

        physicsQuestions.add(new Question("What is kinetic energy?",
                "Energy of position",
                "Energy stored in atoms",
                "Energy of motion",
                "Energy from heat",
                3));

        physicsQuestions.add(new Question("What does the symbol 'g' represent in physics?",
                "Gravitational constant",
                "Acceleration due to gravity",
                "Mass of an object",
                "Work done",
                2));

        physicsQuestions.add(new Question("What happens to the pressure of a gas if volume decreases?",
                "It increases",
                "It decreases",
                "It stays the same",
                "It becomes zero",
                1));

        physicsQuestions.add(new Question("What is the unit of electric current?",
                "Ohm",
                "Watt",
                "Ampere",
                "Volt",
                3));

        physicsQuestions.add(new Question("What kind of energy is stored in a stretched spring?",
                "Kinetic energy",
                "Thermal energy",
                "Potential energy",
                "Nuclear energy",
                3));

        physicsQuestions.add(new Question("Who discovered radioactivity?",
                "Newton",
                "Marie Curie",
                "Einstein",
                "Faraday",
                2));

        physicsQuestions.add(new Question("What is the main source of energy on Earth?",
                "Nuclear power",
                "Wind",
                "Sun",
                "Coal",
                3));

        physicsQuestions.add(new Question("Which of the following is a scalar quantity?",
                "Force",
                "Displacement",
                "Velocity",
                "Mass",
                4));

// 20 down...
        physicsQuestions.add(new Question("What does E=mc² represent?",
                "Energy of sound",
                "Mass of light",
                "Mass-energy equivalence",
                "None of the above",
                3));

        physicsQuestions.add(new Question("What is the direction of electric current?",
                "Negative to positive",
                "Positive to negative",
                "Downward",
                "No direction",
                2));

        physicsQuestions.add(new Question("What is a transformer used for?",
                "Changing voltage",
                "Measuring current",
                "Generating electricity",
                "Storing energy",
                1));

        physicsQuestions.add(new Question("Which subatomic particle is found in the nucleus?",
                "Electron",
                "Proton",
                "Photon",
                "Neutrino",
                2));

        physicsQuestions.add(new Question("How many laws of motion did Newton formulate?",
                "One",
                "Two",
                "Three",
                "Four",
                3));

        physicsQuestions.add(new Question("Which device is used to measure current?",
                "Voltmeter",
                "Ammeter",
                "Barometer",
                "Thermometer",
                2));

        physicsQuestions.add(new Question("What is the function of a fuse in an electrical circuit?",
                "Generate electricity",
                "Store energy",
                "Protect circuit from overcurrent",
                "Convert AC to DC",
                3));

        physicsQuestions.add(new Question("Which quantity has both magnitude and direction?",
                "Mass",
                "Speed",
                "Time",
                "Velocity",
                4));

        physicsQuestions.add(new Question("What type of circuit allows current to flow in only one path?",
                "Series circuit",
                "Parallel circuit",
                "Short circuit",
                "Open circuit",
                1));

        physicsQuestions.add(new Question("Which color of light has the longest wavelength?",
                "Violet",
                "Blue",
                "Red",
                "Green",
                3));

// 30 down...
        physicsQuestions.add(new Question("Which scientist is known for the uncertainty principle?",
                "Bohr",
                "Einstein",
                "Heisenberg",
                "Tesla",
                3));

        physicsQuestions.add(new Question("What is the unit of frequency?",
                "Watt",
                "Hertz",
                "Volt",
                "Ohm",
                2));

        physicsQuestions.add(new Question("What causes an object to accelerate?",
                "Friction",
                "Gravity",
                "Unbalanced force",
                "Constant speed",
                3));

        physicsQuestions.add(new Question("What happens to light when it passes through a prism?",
                "It bends backward",
                "It gets absorbed",
                "It disperses into a spectrum",
                "It reflects",
                3));

        physicsQuestions.add(new Question("What is absolute zero?",
                "0°C",
                "273°C",
                "-273.15°C",
                "-100°C",
                3));

        physicsQuestions.add(new Question("What is the function of a capacitor?",
                "Supply power",
                "Store charge",
                "Convert energy",
                "Reduce voltage",
                2));

        physicsQuestions.add(new Question("What property of a wave determines its pitch?",
                "Amplitude",
                "Wavelength",
                "Frequency",
                "Speed",
                3));

        physicsQuestions.add(new Question("Which part of the atom is positively charged?",
                "Electron",
                "Neutron",
                "Proton",
                "Quark",
                3));

        physicsQuestions.add(new Question("What does a spectrometer measure?",
                "Speed",
                "Voltage",
                "Spectrum of light",
                "Temperature",
                3));

        physicsQuestions.add(new Question("Which phenomenon explains why stars twinkle?",
                "Reflection",
                "Refraction",
                "Diffraction",
                "Scattering",
                2));

// Final 5...
        physicsQuestions.add(new Question("Which type of energy is used in solar panels?",
                "Chemical",
                "Thermal",
                "Kinetic",
                "Light",
                4));

        physicsQuestions.add(new Question("Which scientist is known for the theory of relativity?",
                "Bohr",
                "Einstein",
                "Newton",
                "Curie",
                2));

        physicsQuestions.add(new Question("What is the resistance of a superconductor?",
                "Very high",
                "Infinite",
                "Zero",
                "Variable",
                3));

        physicsQuestions.add(new Question("What does a seismograph measure?",
                "Sound waves",
                "Electric current",
                "Light intensity",
                "Earthquakes",
                4));

        physicsQuestions.add(new Question("What type of mirror is used in car headlights?",
                "Concave",
                "Convex",
                "Flat",
                "Plane",
                1));
        // Increased by 1
        quizzes.put("Physics", physicsQuestions);

        // Chemistry Questions
        ArrayList<Question> chemistryQuestions = new ArrayList<>();

        // 45 Chemistry Questions

        chemistryQuestions.add(new Question(
                "What is the chemical symbol for gold?",
                "Go",
                "Gd",
                "Au",
                "Ag",
                3));

        chemistryQuestions.add(new Question(
                "What is the most abundant gas in Earth's atmosphere?",
                "Oxygen",
                "Carbon dioxide",
                "Nitrogen",
                "Argon",
                3));

        chemistryQuestions.add(new Question(
                "What is the atomic number of carbon?",
                "4",
                "6",
                "8",
                "12",
                2));

        chemistryQuestions.add(new Question(
                "Which element has the symbol Na?",
                "Nickel",
                "Sodium",
                "Nitrogen",
                "Neon",
                2));

        chemistryQuestions.add(new Question(
                "What is the pH of pure water?",
                "6",
                "7",
                "8",
                "9",
                2));

        chemistryQuestions.add(new Question(
                "What type of bond forms between a metal and non-metal?",
                "Covalent bond",
                "Ionic bond",
                "Metallic bond",
                "Hydrogen bond",
                2));

        chemistryQuestions.add(new Question(
                "What is the lightest element?",
                "Helium",
                "Hydrogen",
                "Lithium",
                "Beryllium",
                2));

        chemistryQuestions.add(new Question(
                "Which gas is produced when zinc reacts with hydrochloric acid?",
                "Oxygen",
                "Hydrogen",
                "Carbon dioxide",
                "Nitrogen",
                2));

        chemistryQuestions.add(new Question(
                "What is the chemical formula for water?",
                "H2O",
                "H2O2",
                "HO2",
                "H3O",
                1));

        chemistryQuestions.add(new Question(
                "Which element is essential for photosynthesis?",
                "Iron",
                "Magnesium",
                "Calcium",
                "Potassium",
                2));

        chemistryQuestions.add(new Question(
                "What is the most electronegative element?",
                "Oxygen",
                "Fluorine",
                "Chlorine",
                "Nitrogen",
                2));

        chemistryQuestions.add(new Question(
                "What is the chemical symbol for silver?",
                "Si",
                "Ag",
                "Au",
                "Al",
                2));

        chemistryQuestions.add(new Question(
                "Which acid is found in vinegar?",
                "Citric acid",
                "Acetic acid",
                "Sulfuric acid",
                "Nitric acid",
                2));

        chemistryQuestions.add(new Question(
                "What is Avogadro's number approximately?",
                "6.02 × 10^23",
                "6.02 × 10^22",
                "6.02 × 10^24",
                "6.02 × 10^21",
                1));

        chemistryQuestions.add(new Question(
                "Which noble gas is most abundant in air?",
                "Helium",
                "Neon",
                "Argon",
                "Krypton",
                3));

        chemistryQuestions.add(new Question(
                "What is the chemical formula for table salt?",
                "NaCl",
                "KCl",
                "CaCl2",
                "MgCl2",
                1));

        chemistryQuestions.add(new Question(
                "Which element has the highest melting point?",
                "Iron",
                "Tungsten",
                "Carbon",
                "Platinum",
                2));

        chemistryQuestions.add(new Question(
                "What is the process of a solid directly becoming a gas?",
                "Evaporation",
                "Sublimation",
                "Condensation",
                "Deposition",
                2));

        chemistryQuestions.add(new Question(
                "Which vitamin is produced when skin is exposed to sunlight?",
                "Vitamin C",
                "Vitamin D",
                "Vitamin E",
                "Vitamin K",
                2));

        chemistryQuestions.add(new Question(
                "What is the chemical symbol for iron?",
                "Ir",
                "Fe",
                "In",
                "I",
                2));

        chemistryQuestions.add(new Question(
                "Which gas is responsible for the greenhouse effect?",
                "Oxygen",
                "Carbon dioxide",
                "Nitrogen",
                "Hydrogen",
                2));

        chemistryQuestions.add(new Question(
                "What is the hardest natural substance?",
                "Quartz",
                "Diamond",
                "Graphite",
                "Corundum",
                2));

        chemistryQuestions.add(new Question(
                "Which element is liquid at room temperature besides mercury?",
                "Gallium",
                "Bromine",
                "Cesium",
                "Francium",
                2));

        chemistryQuestions.add(new Question(
                "What is the chemical formula for methane?",
                "CH4",
                "C2H6",
                "C2H4",
                "C3H8",
                1));

        chemistryQuestions.add(new Question(
                "Which acid is found in citrus fruits?",
                "Malic acid",
                "Citric acid",
                "Tartaric acid",
                "Lactic acid",
                2));

        chemistryQuestions.add(new Question(
                "What is the most abundant element in the universe?",
                "Oxygen",
                "Hydrogen",
                "Carbon",
                "Helium",
                2));

        chemistryQuestions.add(new Question(
                "Which element has the symbol K?",
                "Krypton",
                "Potassium",
                "Calcium",
                "Cobalt",
                2));

        chemistryQuestions.add(new Question(
                "What is the chemical formula for ammonia?",
                "NH3",
                "NH4",
                "N2H4",
                "NO3",
                1));

        chemistryQuestions.add(new Question(
                "Which type of reaction releases heat?",
                "Endothermic",
                "Exothermic",
                "Isothermic",
                "Adiabatic",
                2));

        chemistryQuestions.add(new Question(
                "What is the atomic number of oxygen?",
                "6",
                "8",
                "10",
                "12",
                2));

        chemistryQuestions.add(new Question(
                "Which element is used in pencils?",
                "Lead",
                "Carbon",
                "Silicon",
                "Tin",
                2));

        chemistryQuestions.add(new Question(
                "What is the chemical symbol for copper?",
                "Co",
                "Cu",
                "Cr",
                "Ca",
                2));

        chemistryQuestions.add(new Question(
                "Which gas makes up about 21% of Earth's atmosphere?",
                "Nitrogen",
                "Oxygen",
                "Carbon dioxide",
                "Argon",
                2));

        chemistryQuestions.add(new Question(
                "What is the main component of natural gas?",
                "Ethane",
                "Methane",
                "Propane",
                "Butane",
                2));

        chemistryQuestions.add(new Question(
                "Which element has the symbol Pb?",
                "Platinum",
                "Lead",
                "Phosphorus",
                "Palladium",
                2));

        chemistryQuestions.add(new Question(
                "What is the chemical formula for carbon dioxide?",
                "CO",
                "CO2",
                "C2O",
                "C2O2",
                2));

        chemistryQuestions.add(new Question(
                "Which acid is found in the stomach?",
                "Sulfuric acid",
                "Hydrochloric acid",
                "Nitric acid",
                "Phosphoric acid",
                2));

        chemistryQuestions.add(new Question(
                "What is the study of carbon compounds called?",
                "Inorganic chemistry",
                "Organic chemistry",
                "Physical chemistry",
                "Analytical chemistry",
                2));

        chemistryQuestions.add(new Question(
                "Which element has the highest atomic number naturally occurring?",
                "Uranium",
                "Plutonium",
                "Thorium",
                "Radium",
                1));

        chemistryQuestions.add(new Question(
                "What is the chemical symbol for helium?",
                "H",
                "He",
                "Hl",
                "Hm",
                2));

        chemistryQuestions.add(new Question(
                "Which compound is known as dry ice?",
                "Frozen water",
                "Solid carbon dioxide",
                "Frozen nitrogen",
                "Solid methane",
                2));

        chemistryQuestions.add(new Question(
                "What is the molecular formula for glucose?",
                "C6H12O6",
                "C12H22O11",
                "C6H6O6",
                "C5H10O5",
                1));

        chemistryQuestions.add(new Question(
                "Which element is essential for thyroid function?",
                "Iron",
                "Iodine",
                "Calcium",
                "Zinc",
                2));

        chemistryQuestions.add(new Question(
                "What is the chemical name for rust?",
                "Iron sulfide",
                "Iron oxide",
                "Iron carbonate",
                "Iron nitrate",
                2));

        chemistryQuestions.add(new Question(
                "Which gas is released during photosynthesis?",
                "Carbon dioxide",
                "Oxygen",
                "Nitrogen",
                "Hydrogen",
                2));

        quizzes.put("Chemistry", chemistryQuestions);

        // Mathematics Questions
        ArrayList<Question> mathsQuestions = new ArrayList<>();
        // 45 Mathematics Questions
        mathsQuestions.add(new Question(
                "What is 25% of 200?",
                "25",
                "40",
                "50",
                "75",
                3));

        mathsQuestions.add(new Question(
                "What is the square root of 144?",
                "10",
                "11",
                "12",
                "13",
                3));

        mathsQuestions.add(new Question(
                "What is 7 × 8?",
                "54",
                "55",
                "56",
                "57",
                3));

        mathsQuestions.add(new Question(
                "What is the value of π (pi) to 2 decimal places?",
                "3.12",
                "3.14",
                "3.16",
                "3.18",
                2));

        mathsQuestions.add(new Question(
                "What is 15² (15 squared)?",
                "215",
                "225",
                "235",
                "245",
                2));

        mathsQuestions.add(new Question(
                "What is 180 ÷ 12?",
                "14",
                "15",
                "16",
                "17",
                2));

        mathsQuestions.add(new Question(
                "What is the sum of angles in a triangle?",
                "90°",
                "180°",
                "270°",
                "360°",
                2));

        mathsQuestions.add(new Question(
                "What is 2³ (2 to the power of 3)?",
                "6",
                "8",
                "9",
                "12",
                2));

        mathsQuestions.add(new Question(
                "What is 5! (5 factorial)?",
                "100",
                "110",
                "120",
                "130",
                3));

        mathsQuestions.add(new Question(
                "What is the derivative of x²?",
                "x",
                "2x",
                "x²",
                "2x²",
                2));

        mathsQuestions.add(new Question(
                "What is 3/4 as a decimal?",
                "0.65",
                "0.70",
                "0.75",
                "0.80",
                3));

        mathsQuestions.add(new Question(
                "What is the circumference formula for a circle?",
                "πr",
                "2πr",
                "πr²",
                "2πr²",
                2));

        mathsQuestions.add(new Question(
                "What is log₁₀(100)?",
                "1",
                "2",
                "10",
                "100",
                2));

        mathsQuestions.add(new Question(
                "What is the slope of the line y = 3x + 5?",
                "1",
                "3",
                "5",
                "8",
                2));

        mathsQuestions.add(new Question(
                "What is 60% of 150?",
                "80",
                "85",
                "90",
                "95",
                3));

        mathsQuestions.add(new Question(
                "What is the area of a square with side length 6?",
                "24",
                "30",
                "36",
                "42",
                3));

        mathsQuestions.add(new Question(
                "What is √(49)?",
                "6",
                "7",
                "8",
                "9",
                2));

        mathsQuestions.add(new Question(
                "What is the next prime number after 17?",
                "18",
                "19",
                "20",
                "21",
                2));

        mathsQuestions.add(new Question(
                "What is 9 × 12?",
                "106",
                "107",
                "108",
                "109",
                3));

        mathsQuestions.add(new Question(
                "What is the integral of 2x?",
                "x",
                "x²",
                "2x²",
                "x² + C",
                4));

        mathsQuestions.add(new Question(
                "What is 144 ÷ 16?",
                "8",
                "9",
                "10",
                "11",
                2));

        mathsQuestions.add(new Question(
                "What is the sum of the first 10 natural numbers?",
                "45",
                "50",
                "55",
                "60",
                3));

        mathsQuestions.add(new Question(
                "What is sin(90°)?",
                "0",
                "1",
                "√2/2",
                "√3/2",
                2));

        mathsQuestions.add(new Question(
                "What is 4³ (4 cubed)?",
                "12",
                "16",
                "48",
                "64",
                4));

        mathsQuestions.add(new Question(
                "What is the area of a circle with radius 3?",
                "6π",
                "9π",
                "12π",
                "18π",
                2));

        mathsQuestions.add(new Question(
                "What is 13 + 29?",
                "41",
                "42",
                "43",
                "44",
                2));

        mathsQuestions.add(new Question(
                "What is the hypotenuse of a right triangle with legs 3 and 4?",
                "5",
                "6",
                "7",
                "8",
                1));

        mathsQuestions.add(new Question(
                "What is 0.25 as a fraction?",
                "1/3",
                "1/4",
                "1/5",
                "2/5",
                2));

        mathsQuestions.add(new Question(
                "What is cos(0°)?",
                "0",
                "1",
                "√2/2",
                "√3/2",
                2));

        mathsQuestions.add(new Question(
                "What is 11²?",
                "111",
                "121",
                "131",
                "141",
                2));

        mathsQuestions.add(new Question(
                "What is the volume of a cube with side length 4?",
                "16",
                "32",
                "48",
                "64",
                4));

        mathsQuestions.add(new Question(
                "What is 216 ÷ 18?",
                "11",
                "12",
                "13",
                "14",
                2));

        mathsQuestions.add(new Question(
                "What is the GCD of 24 and 36?",
                "6",
                "8",
                "12",
                "18",
                3));

        mathsQuestions.add(new Question(
                "What is tan(45°)?",
                "0",
                "1",
                "√2",
                "√3",
                2));

        mathsQuestions.add(new Question(
                "What is 2⁵ (2 to the power of 5)?",
                "16",
                "24",
                "32",
                "40",
                3));

        mathsQuestions.add(new Question(
                "What is the perimeter of a rectangle with length 8 and width 5?",
                "21",
                "24",
                "26",
                "28",
                3));

        mathsQuestions.add(new Question(
                "What is √(81)?",
                "7",
                "8",
                "9",
                "10",
                3));

        mathsQuestions.add(new Question(
                "What is 40% of 250?",
                "90",
                "95",
                "100",
                "105",
                3));

        mathsQuestions.add(new Question(
                "What is the LCM of 6 and 8?",
                "14",
                "18",
                "24",
                "48",
                3));

        mathsQuestions.add(new Question(
                "What is 17 × 6?",
                "96",
                "102",
                "108",
                "114",
                2));

        mathsQuestions.add(new Question(
                "What is the surface area of a sphere with radius 2?",
                "8π",
                "12π",
                "16π",
                "20π",
                3));

        mathsQuestions.add(new Question(
                "What is 169 ÷ 13?",
                "11",
                "12",
                "13",
                "14",
                3));

        mathsQuestions.add(new Question(
                "What is e (Euler's number) to 2 decimal places?",
                "2.61",
                "2.72",
                "2.83",
                "2.94",
                2));

        mathsQuestions.add(new Question(
                "What is 6! (6 factorial)?",
                "620",
                "680",
                "720",
                "780",
                3));

        mathsQuestions.add(new Question(
                "What is the distance between points (0,0) and (3,4)?",
                "4",
                "5",
                "6",
                "7",
                2));

        quizzes.put("Maths", mathsQuestions);

        // Geography Questions
        ArrayList<Question> geographyQuestions = new ArrayList<>();
        // 45 Geography Questions

        geographyQuestions.add(new Question(
                "What is the largest continent by area?",
                "Africa",
                "Asia",
                "North America",
                "Europe",
                2));

        geographyQuestions.add(new Question(
                "Which river is the longest in the world?",
                "Amazon River",
                "Nile River",
                "Mississippi River",
                "Yangtze River",
                2));

        geographyQuestions.add(new Question(
                "What is the capital of Australia?",
                "Sydney",
                "Melbourne",
                "Canberra",
                "Perth",
                3));

        geographyQuestions.add(new Question(
                "Which mountain range contains Mount Everest?",
                "Andes",
                "Himalayas",
                "Rocky Mountains",
                "Alps",
                2));

        geographyQuestions.add(new Question(
                "What is the smallest country in the world?",
                "Monaco",
                "Vatican City",
                "San Marino",
                "Liechtenstein",
                2));

        geographyQuestions.add(new Question(
                "Which ocean is the largest?",
                "Atlantic Ocean",
                "Indian Ocean",
                "Pacific Ocean",
                "Arctic Ocean",
                3));

        geographyQuestions.add(new Question(
                "What is the capital of Canada?",
                "Toronto",
                "Vancouver",
                "Ottawa",
                "Montreal",
                3));

        geographyQuestions.add(new Question(
                "Which desert is the largest in the world?",
                "Sahara Desert",
                "Gobi Desert",
                "Kalahari Desert",
                "Arabian Desert",
                1));

        geographyQuestions.add(new Question(
                "What is the deepest ocean trench?",
                "Puerto Rico Trench",
                "Mariana Trench",
                "Japan Trench",
                "Peru-Chile Trench",
                2));

        geographyQuestions.add(new Question(
                "Which country has the most time zones?",
                "Russia",
                "United States",
                "China",
                "France",
                4));

        geographyQuestions.add(new Question(
                "What is the highest waterfall in the world?",
                "Niagara Falls",
                "Victoria Falls",
                "Angel Falls",
                "Iguazu Falls",
                3));

        geographyQuestions.add(new Question(
                "Which strait separates Europe and Africa?",
                "Strait of Gibraltar",
                "Bosphorus Strait",
                "Strait of Hormuz",
                "Dover Strait",
                1));

        geographyQuestions.add(new Question(
                "What is the largest island in the world?",
                "Madagascar",
                "Greenland",
                "New Guinea",
                "Borneo",
                2));

        geographyQuestions.add(new Question(
                "Which city is known as the 'Pearl of the Orient'?",
                "Singapore",
                "Hong Kong",
                "Bangkok",
                "Manila",
                2));

        geographyQuestions.add(new Question(
                "What is the lowest point on Earth's surface?",
                "Death Valley",
                "Dead Sea",
                "Caspian Sea",
                "Lake Assal",
                2));

        geographyQuestions.add(new Question(
                "Which country is entirely surrounded by South Africa?",
                "Swaziland",
                "Lesotho",
                "Botswana",
                "Namibia",
                2));

        geographyQuestions.add(new Question(
                "What is the largest lake in Africa?",
                "Lake Chad",
                "Lake Tanganyika",
                "Lake Victoria",
                "Lake Malawi",
                3));

        geographyQuestions.add(new Question(
                "Which European capital is divided by the Danube River?",
                "Vienna",
                "Budapest",
                "Belgrade",
                "Bratislava",
                2));

        geographyQuestions.add(new Question(
                "What is the most populous city in the world?",
                "Mumbai",
                "Tokyo",
                "Shanghai",
                "Delhi",
                2));

        geographyQuestions.add(new Question(
                "Which country has the longest coastline?",
                "Russia",
                "Canada",
                "Australia",
                "Norway",
                2));

        geographyQuestions.add(new Question(
                "What is the capital of Brazil?",
                "São Paulo",
                "Rio de Janeiro",
                "Brasília",
                "Salvador",
                3));

        geographyQuestions.add(new Question(
                "Which sea is between Italy and the Balkans?",
                "Tyrrhenian Sea",
                "Adriatic Sea",
                "Ionian Sea",
                "Aegean Sea",
                2));

        geographyQuestions.add(new Question(
                "What is the largest country in South America?",
                "Argentina",
                "Brazil",
                "Peru",
                "Colombia",
                2));

        geographyQuestions.add(new Question(
                "Which African country was never colonized?",
                "Liberia",
                "Ethiopia",
                "Morocco",
                "Egypt",
                2));

        geographyQuestions.add(new Question(
                "What is the highest peak in North America?",
                "Mount McKinley",
                "Mount Whitney",
                "Mount Elbert",
                "Mount Washington",
                1));

        geographyQuestions.add(new Question(
                "Which city straddles two continents?",
                "Cairo",
                "Istanbul",
                "Suez",
                "Casablanca",
                2));

        geographyQuestions.add(new Question(
                "What is the largest bay in the world?",
                "Bay of Bengal",
                "Hudson Bay",
                "Gulf of Mexico",
                "Bay of Biscay",
                1));

        geographyQuestions.add(new Question(
                "Which country has three capital cities?",
                "Bolivia",
                "South Africa",
                "Switzerland",
                "Netherlands",
                2));

        geographyQuestions.add(new Question(
                "What is the driest place on Earth?",
                "Death Valley",
                "Sahara Desert",
                "Atacama Desert",
                "Gobi Desert",
                3));

        geographyQuestions.add(new Question(
                "Which river flows through the Grand Canyon?",
                "Mississippi River",
                "Colorado River",
                "Missouri River",
                "Rio Grande",
                2));

        geographyQuestions.add(new Question(
                "What is the largest peninsula in the world?",
                "Iberian Peninsula",
                "Arabian Peninsula",
                "Indian Peninsula",
                "Scandinavian Peninsula",
                2));

        geographyQuestions.add(new Question(
                "Which country is known as the Land of the Rising Sun?",
                "China",
                "Japan",
                "South Korea",
                "Thailand",
                2));

        geographyQuestions.add(new Question(
                "What is the southernmost continent?",
                "South America",
                "Australia",
                "Antarctica",
                "Africa",
                3));

        geographyQuestions.add(new Question(
                "Which European country has the most islands?",
                "Norway",
                "Finland",
                "Sweden",
                "Denmark",
                2));

        geographyQuestions.add(new Question(
                "What is the largest volcanic crater lake?",
                "Crater Lake",
                "Lake Toba",
                "Laguna de Bay",
                "Lake Taupo",
                2));

        geographyQuestions.add(new Question(
                "Which country is home to Machu Picchu?",
                "Bolivia",
                "Peru",
                "Ecuador",
                "Colombia",
                2));

        geographyQuestions.add(new Question(
                "What is the windiest place on Earth?",
                "Mount Washington",
                "Antarctica",
                "Patagonia",
                "Mount Everest",
                2));

        geographyQuestions.add(new Question(
                "Which sea is the saltiest?",
                "Red Sea",
                "Dead Sea",
                "Caspian Sea",
                "Mediterranean Sea",
                2));

        geographyQuestions.add(new Question(
                "What is the largest coral reef system?",
                "Mesoamerican Reef",
                "Great Barrier Reef",
                "Caribbean Coral Reef",
                "Red Sea Coral Reef",
                2));

        geographyQuestions.add(new Question(
                "Which country has the most volcanoes?",
                "Japan",
                "Indonesia",
                "Philippines",
                "Chile",
                2));

        geographyQuestions.add(new Question(
                "What is the largest freshwater lake by volume?",
                "Lake Superior",
                "Lake Baikal",
                "Lake Tanganyika",
                "Caspian Sea",
                2));

        geographyQuestions.add(new Question(
                "Which country is known as the Roof of the World?",
                "Nepal",
                "Tibet",
                "Bhutan",
                "Afghanistan",
                2));

        geographyQuestions.add(new Question(
                "What is the most linguistically diverse country?",
                "India",
                "Papua New Guinea",
                "Indonesia",
                "Nigeria",
                2));

        geographyQuestions.add(new Question(
                "Which river delta is the largest in the world?",
                "Nile Delta",
                "Ganges Delta",
                "Mississippi Delta",
                "Amazon Delta",
                2));

        geographyQuestions.add(new Question(
                "What is the flattest country in the world?",
                "Netherlands",
                "Maldives",
                "Denmark",
                "Bangladesh",
                2));

        quizzes.put("Geography", geographyQuestions);

        // Biology Questions
        ArrayList<Question> biologyQuestions = new ArrayList<>();
        // 45 Biology Questions

        biologyQuestions.add(new Question(
                "Which blood cells fight infection?",
                "Red blood cells",
                "White blood cells",
                "Platelets",
                "Plasma",
                2));

        biologyQuestions.add(new Question(
                "What is the powerhouse of the cell?",
                "Nucleus",
                "Mitochondria",
                "Ribosome",
                "Golgi apparatus",
                2));

        biologyQuestions.add(new Question(
                "What is the basic unit of life?",
                "Tissue",
                "Cell",
                "Organ",
                "Organism",
                2));

        biologyQuestions.add(new Question(
                "Which process do plants use to make food?",
                "Respiration",
                "Photosynthesis",
                "Transpiration",
                "Digestion",
                2));

        biologyQuestions.add(new Question(
                "What is DNA's full name?",
                "Deoxyribonucleic acid",
                "Diribonucleic acid",
                "Deoxyribose nucleic acid",
                "Double ribonucleic acid",
                1));

        biologyQuestions.add(new Question(
                "How many chambers does a human heart have?",
                "2",
                "4",
                "6",
                "8",
                2));

        biologyQuestions.add(new Question(
                "Which organ produces insulin?",
                "Liver",
                "Pancreas",
                "Kidney",
                "Spleen",
                2));

        biologyQuestions.add(new Question(
                "What is the largest organ in the human body?",
                "Liver",
                "Skin",
                "Brain",
                "Lungs",
                2));

        biologyQuestions.add(new Question(
                "Which gas do plants absorb during photosynthesis?",
                "Oxygen",
                "Carbon dioxide",
                "Nitrogen",
                "Hydrogen",
                2));

        biologyQuestions.add(new Question(
                "What is the study of fungi called?",
                "Botany",
                "Mycology",
                "Zoology",
                "Ecology",
                2));

        biologyQuestions.add(new Question(
                "Which type of blood vessel carries blood away from the heart?",
                "Veins",
                "Arteries",
                "Capillaries",
                "Venules",
                2));

        biologyQuestions.add(new Question(
                "What is the green pigment in plants called?",
                "Carotene",
                "Chlorophyll",
                "Xanthophyll",
                "Anthocyanin",
                2));

        biologyQuestions.add(new Question(
                "How many chromosomes do humans normally have?",
                "44",
                "46",
                "48",
                "50",
                2));

        biologyQuestions.add(new Question(
                "Which scientist is known for the theory of evolution?",
                "Mendel",
                "Darwin",
                "Watson",
                "Pasteur",
                2));

        biologyQuestions.add(new Question(
                "What is the largest mammal in the world?",
                "African elephant",
                "Blue whale",
                "Giraffe",
                "Polar bear",
                2));

        biologyQuestions.add(new Question(
                "Which part of the brain controls balance?",
                "Cerebrum",
                "Cerebellum",
                "Medulla",
                "Hypothalamus",
                2));

        biologyQuestions.add(new Question(
                "What type of organism is a mushroom?",
                "Plant",
                "Fungus",
                "Animal",
                "Bacteria",
                2));

        biologyQuestions.add(new Question(
                "Which vitamin prevents scurvy?",
                "Vitamin A",
                "Vitamin C",
                "Vitamin D",
                "Vitamin K",
                2));

        biologyQuestions.add(new Question(
                "What is the longest bone in the human body?",
                "Tibia",
                "Femur",
                "Humerus",
                "Fibula",
                2));

        biologyQuestions.add(new Question(
                "Which organelle contains the cell's genetic material?",
                "Mitochondria",
                "Nucleus",
                "Ribosome",
                "Lysosome",
                2));

        biologyQuestions.add(new Question(
                "What is the process by which cells divide?",
                "Meiosis",
                "Mitosis",
                "Osmosis",
                "Diffusion",
                2));

        biologyQuestions.add(new Question(
                "Which hormone regulates blood sugar levels?",
                "Adrenaline",
                "Insulin",
                "Cortisol",
                "Thyroxine",
                2));

        biologyQuestions.add(new Question(
                "What is the smallest unit of classification?",
                "Genus",
                "Species",
                "Family",
                "Order",
                2));

        biologyQuestions.add(new Question(
                "Which part of the eye controls the amount of light entering?",
                "Cornea",
                "Iris",
                "Lens",
                "Retina",
                2));

        biologyQuestions.add(new Question(
                "What is the main function of red blood cells?",
                "Fight infection",
                "Transport oxygen",
                "Clot blood",
                "Produce antibodies",
                2));

        biologyQuestions.add(new Question(
                "Which kingdom do bacteria belong to?",
                "Plantae",
                "Monera",
                "Animalia",
                "Fungi",
                2));

        biologyQuestions.add(new Question(
                "What is the hardest substance in the human body?",
                "Bone",
                "Tooth enamel",
                "Cartilage",
                "Keratin",
                2));

        biologyQuestions.add(new Question(
                "Which process removes waste from the blood?",
                "Digestion",
                "Filtration",
                "Respiration",
                "Circulation",
                2));

        biologyQuestions.add(new Question(
                "What is the fastest land animal?",
                "Lion",
                "Cheetah",
                "Leopard",
                "Gazelle",
                2));

        biologyQuestions.add(new Question(
                "Which gland is known as the master gland?",
                "Thyroid",
                "Pituitary",
                "Adrenal",
                "Parathyroid",
                2));

        biologyQuestions.add(new Question(
                "What is the study of heredity called?",
                "Ecology",
                "Genetics",
                "Anatomy",
                "Physiology",
                2));

        biologyQuestions.add(new Question(
                "Which type of muscle is found in the heart?",
                "Skeletal",
                "Cardiac",
                "Smooth",
                "Voluntary",
                2));

        biologyQuestions.add(new Question(
                "What is the gestation period of a human pregnancy?",
                "8 months",
                "9 months",
                "10 months",
                "11 months",
                2));

        biologyQuestions.add(new Question(
                "Which part of the plant conducts water?",
                "Phloem",
                "Xylem",
                "Cambium",
                "Epidermis",
                2));

        biologyQuestions.add(new Question(
                "What is the largest bird in the world?",
                "Eagle",
                "Ostrich",
                "Condor",
                "Albatross",
                2));

        biologyQuestions.add(new Question(
                "Which organ is responsible for detoxification?",
                "Kidney",
                "Liver",
                "Spleen",
                "Pancreas",
                2));

        biologyQuestions.add(new Question(
                "What is the basic unit of the nervous system?",
                "Synapse",
                "Neuron",
                "Dendrite",
                "Axon",
                2));

        biologyQuestions.add(new Question(
                "Which process involves the breakdown of glucose?",
                "Photosynthesis",
                "Cellular respiration",
                "Transpiration",
                "Osmosis",
                2));

        biologyQuestions.add(new Question(
                "What is the scientific name for humans?",
                "Homo erectus",
                "Homo sapiens",
                "Homo habilis",
                "Homo neanderthalensis",
                2));

        biologyQuestions.add(new Question(
                "Which enzyme breaks down starch?",
                "Pepsin",
                "Amylase",
                "Lipase",
                "Trypsin",
                2));

        biologyQuestions.add(new Question(
                "What is the fluid that surrounds the brain called?",
                "Blood",
                "Cerebrospinal fluid",
                "Lymph",
                "Plasma",
                2));

        biologyQuestions.add(new Question(
                "Which animal can regenerate its tail?",
                "Snake",
                "Lizard",
                "Frog",
                "Bird",
                2));

        biologyQuestions.add(new Question(
                "What is the smallest bone in the human body?",
                "Stirrup bone",
                "Hammer bone",
                "Anvil bone",
                "Jaw bone",
                1));

        biologyQuestions.add(new Question(
                "Which blood type is considered the universal donor?",
                "A",
                "O",
                "B",
                "AB",
                2));

        biologyQuestions.add(new Question(
                "What is the process of water movement through plants called?",
                "Osmosis",
                "Transpiration",
                "Diffusion",
                "Active transport",
                2));

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
            String correctAnswer = options[original.getCorrectAnswer() - 1];

            // Ստեղծում ենք տարբերակների ArrayList և խառնում այն
            ArrayList<String> shuffledOptions = new ArrayList<>();
            for (String option : options) {
                shuffledOptions.add(option);
            }
            Collections.shuffle(shuffledOptions);

            // Գտնում ենք ճիշտ պատասխանի նոր ինդեքսը խառնված տարբերակներում
            int newCorrectIndex = -1;
            for (int i = 0; i < shuffledOptions.size(); i++) {
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
                        newCorrectIndex + 1
                ));
            }
        }

        // Խառնում ենք հարցերի հերթականությունը
        Collections.shuffle(shuffledQuestions);

        // Վերադարձնում ենք միայն առաջին 15 հարցերը
        int questionsToReturn = Math.min(15, shuffledQuestions.size());
        return new ArrayList<>(shuffledQuestions.subList(0, questionsToReturn));
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