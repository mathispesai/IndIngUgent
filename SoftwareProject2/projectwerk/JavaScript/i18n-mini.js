const i18n = (() => {
    let currentLang = 'nl';
    const translations = {};

    const languageMap = {
        nl: 'nl.json',
        en: 'en.json',
        fr: 'fr.json',
        de: 'de.json'
    };

    // Aangepaste functie om de vertaling van placeholders te ondersteunen
    function translatePlaceholders() {
        const placeholders = document.querySelectorAll('[data-i18n-placeholder]');
        placeholders.forEach(input => {
            const placeholderKey = input.getAttribute('data-i18n-placeholder');
            const translatedPlaceholder = translate(placeholderKey); // Krijg de vertaling op basis van de sleutel
            input.setAttribute('placeholder', translatedPlaceholder);
        });
    }



    function changeLanguage(langKey) {
        const fileName = languageMap[langKey];
        if (!fileName) {
            console.warn(`No translation file mapped for key: ${langKey}`);
            return;
        }

        fetch(`../JavaScript/locales/${fileName}`)
            .then(response => response.json())
            .then(data => {
                setTranslations(langKey, data);
                setLanguage(langKey);
                document.dispatchEvent(new CustomEvent('languageChanged', { detail: langKey }));
                document.documentElement.lang = langKey;
                localStorage.setItem('preferredLang', langKey);
            })
            .catch(error => {
                console.error(`Error loading language file "${fileName}":`, error);
            });
    }

    function setTranslations(lang, data) {
        translations[lang] = data;
    }

    function setLanguage(lang) {
        currentLang = lang;
        document.dispatchEvent(new CustomEvent('languageChanged', { detail: lang }));
        updateDOM();
    }

    function translate(keyPath) {
        const keys = keyPath.split('.');
        let result = translations[currentLang];

        for (const key of keys) {
            if (result === undefined) return keyPath;
            if (!isNaN(key) && Array.isArray(result)) {
                result = result[parseInt(key)];
            } else if (result[key] !== undefined) {
                result = result[key];
            } else {
                return keyPath;
            }
        }
        return result;
    }

    function updateDOM() {
        document.querySelectorAll('[data-i18n]').forEach((el) => {
            const raw = el.getAttribute('data-i18n');
            let attr = null;
            let key = raw;

            const match = raw.match(/^\[(.+?)\](.+)$/);
            if (match) {
                attr = match[1];
                key = match[2];
            }

            const translation = translate(key);

            if (attr === 'placeholder') {
                el.placeholder = translation;
            } else if (attr === 'html') {
                el.innerHTML = translation;
            } else {
                el.textContent = translation;
            }

            if (el.tagName === 'A' && el.hasAttribute('href')) {
                const href = el.getAttribute('href');
                setTimeout(() => el.setAttribute('href', href), 0);
            }
        });

        ['title', 'alt', 'value'].forEach(attr => {
            document.querySelectorAll(`[data-i18n-${attr}]`).forEach(el => {
                const key = el.getAttribute(`data-i18n-${attr}`);
                const translation = translate(key);
                if (translation !== key) {
                    el.setAttribute(attr, translation);
                }
            });
        });
        translatePlaceholders();
    }

    function init() {
        const saved = localStorage.getItem('preferredLang') || 'nl';
        changeLanguage(saved);
    }

    return {
        changeLanguage,
        setTranslations,
        setLanguage,
        translate,
        updateDOM,
        init,
        t: translate,
    };
})();

document.addEventListener('DOMContentLoaded', () => {
    i18n.init();
});

