export {generateRandomProfile, generateUniqueRandomProfile}

//alle profielen worden bijgehouden om dubbels te vermijden
const generatedProfiles = new Set();

// Functie om een willekeurige voornaam te genereren
function generateRandomFirstName() {
    const firstNamesList = ['Emma', 'Liam', 'Olivia', 'Noah', 'Ava', 'Lucas', 'Isabella', 'Ethan', 'Sophia', 'Mia', 'Alexander', 'Amelia', 'Henry', 'Charlotte', 'Benjamin', 'Harper', 'Michael', 'Evelyn', 'Daniel', 'Abigail', 'James', 'Emily', 'William', 'Madison', 'Jacob', 'Grace', 'Samuel', 'Avery', 'Logan', 'Ella', 'Ryan', 'Aiden', 'Nora', 'Jack', 'Sophie', 'Luke', 'Lily', 'David', 'Grace', 'Joseph', 'Zoe', 'Gabriel', 'Chloe', 'Owen', 'Evelyn', 'Carter', 'Emma', 'Liam', 'Ava', 'Noah', 'Isabella', 'James', 'Sophia', 'Benjamin', 'Mia', 'William', 'Charlotte', 'Elijah', 'Amelia', 'Oliver', 'Harper', 'Lucas', 'Evelyn', 'Mason', 'Emily', 'Logan', 'Abigail', 'Alexander', 'Ella', 'Sebastian', 'Elizabeth', 'Ethan', 'Sofia', 'Jackson', 'Avery', 'Michael', 'Scarlett', 'Ethan', 'Madison', 'Jacob', 'Victoria', 'Daniel', 'Grace', 'Henry', 'Chloe', 'Alexander', 'Zoe', 'William', 'Camila', 'Anthony', 'Penelope', 'Ryan', 'Luna', 'Matthew', 'Hannah', 'David', 'Sofia'];
    return firstNamesList[Math.floor(Math.random() * firstNamesList.length)];
}

// Functie om een willekeurige achternaam te genereren
function generateRandomLastName() {
    const lastNamesList = ['Smith', 'Johnson', 'Williams', 'Brown', 'Jones', 'Garcia', 'Miller', 'Davis', 'Rodriguez', 'Martinez', 'Hernandez', 'Lopez', 'Gonzalez', 'Wilson', 'Anderson', 'Thomas', 'Taylor', 'Moore', 'Jackson', 'Martin', 'Lee', 'Walker', 'Hall', 'Allen', 'Young', 'Wright', 'King', 'Scott', 'Nguyen', 'Baker', 'Green', 'Hill', 'Adams', 'Nelson', 'Carter', 'Mitchell', 'Perez', 'Roberts', 'Turner', 'Phillips', 'Campbell', 'Parker', 'Evans', 'Edwards', 'Collins', 'Stewart', 'Sanchez', 'Morris', 'Rogers', 'Reed', 'Cook', 'Morgan', 'Bell', 'Murphy', 'Bailey', 'Rivera', 'Cooper', 'Richardson', 'Cox', 'Howard', 'Ward', 'Torres', 'Peterson', 'Gray', 'Ramirez', 'James', 'Watson', 'Brooks', 'Kelly', 'Sanders', 'Price', 'Bennett', 'Wood', 'Barnes', 'Ross', 'Henderson', 'Coleman', 'Jenkins', 'Perry', 'Powell', 'Long', 'Patterson', 'Hughes', 'Flores', 'Washington', 'Butler', 'Simmons', 'Foster', 'Gonzales', 'Bryant', 'Alexander', 'Russell', 'Griffin', 'Diaz', 'Hayes', 'Myers', 'Ford', 'Hamilton', 'Graham', 'Sullivan', 'Wallace', 'Woods', 'Cole', 'West', 'Jordan', 'Owens', 'Reynolds'];
    return lastNamesList[Math.floor(Math.random() * lastNamesList.length)];
}

// Functie om een willekeurig e-mailadres te genereren op basis van voornaam en achternaam
function generateRandomEmail(firstName, lastName) {
    const domains = ['gmail.com', 'yahoo.com', 'hotmail.com', 'outlook.com', 'icloud.com', 'mail.com', 'aol.com', 'protonmail.com', 'live.com', 'yandex.com'];
    const randomDomain = domains[Math.floor(Math.random() * domains.length)];
    return `${firstName.toLowerCase()}.${lastName.toLowerCase()}@${randomDomain}`;
}

// Functie om een willekeurige profiel voor een sporter te genereren
function generateRandomProfile() {
    // Lijsten van mogelijke waarden voor interesses, niveaus en leeftijden
    const interestsList = ['voetbal', 'basketbal', 'tennis', 'zwemmen', 'hardlopen', 'volleybal', 'golf', 'fietsen', 'yoga', 'boksen', 'hockey', 'schaken', 'klimmen', 'badminton', 'rugby', 'tafeltennis', 'surfing', 'skiën', 'snowboarden', 'karate'];
    const levelsList = [1, 2, 3, 4, 5];
    const minAge = 18;
    const maxAge = 60;

    // Willekeurige selectie van 1, 2 of 3 unieke interesses
    const numInterests = Math.floor(Math.random() * 3) + 1;
    const selectedInterests = new Set(); // Gebruik een Set om ervoor te zorgen dat interesses uniek zijn
    while (selectedInterests.size < numInterests) {
        const randomIndex = Math.floor(Math.random() * interestsList.length);
        selectedInterests.add(interestsList[randomIndex]);
    }
    const sport = Array.from(selectedInterests); // Converteer de Set terug naar een array

    const level = levelsList[Math.floor(Math.random() * levelsList.length)]; // Willekeurig niveau
    const age = Math.floor(Math.random() * (maxAge - minAge + 1)) + minAge; // Willekeurige leeftijd tussen minAge en maxAge

    // Willekeurige voornaam, achternaam en e-mailadres genereren
    const firstName = generateRandomFirstName();
    const lastName = generateRandomLastName();
    const email = generateRandomEmail(firstName, lastName);

    return { firstName, lastName, email, sport, level, age };
}

// Functie om een uniek willekeurig profiel te genereren
function generateUniqueRandomProfile() {
    let profile;
    do {
        profile = generateRandomProfile();
    } while (generatedProfiles.has(profile.email)); // Controleer of het e-mailadres al is gegenereerd
    generatedProfiles.add(profile.email); // Voeg het e-mailadres toe aan de set van gegenereerde profielen
    return profile;
}