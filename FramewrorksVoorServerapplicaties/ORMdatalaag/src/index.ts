import 'reflect-metadata';
import { AppDataSource } from './data-source';
import app from './server';

// Database-initialisatie
AppDataSource
    .initialize()
    .then(() => {
        console.log('Database is succesvol verbonden!');
    })
    .catch((error) => {
        console.error('Fout tijdens database-initialisatie:', error);
    });

// Start de server
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
    console.log(`Server draait op http://localhost:${PORT}`);
});
