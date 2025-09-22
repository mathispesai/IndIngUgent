import { Router } from 'express';
import { DietController } from './controllers/DietController';
import { UserController } from './controllers/UserController';
import { WorkoutController } from './controllers/WorkoutController';

const router = Router();

// Instantiate controllers
const dietController = new DietController();
const userController = new UserController();
const workoutController = new WorkoutController();

// Routes for Diets
router.get('/diets', dietController.getAll); // GET all diets
router.get('/diets/:id', dietController.getById); // GET diet by ID
router.post('/diets', dietController.create); // POST create a new diet
router.put('/diets/:id', dietController.update); // PUT update a diet
router.delete('/diets/:id', dietController.delete); // DELETE a diet

// Routes for Users
router.get('/users', userController.getAll); // GET all users
router.get('/users/:id', userController.getById); // GET user by ID
router.post('/users', userController.create); // POST create a new user
router.put('/users/:id', userController.update); // PUT update user info
router.delete('/users/:id', userController.delete); // DELETE a user

// Routes for Workouts
router.get('/workouts', workoutController.getAll); // GET all workouts
router.get('/workouts/:id', workoutController.getById); // GET workout by ID
router.post('/workouts', workoutController.create); // POST create a new workout
router.post('/workouts/running', workoutController.createRunning); // POST create a running workout
router.put('/workouts/:id', workoutController.update); // PUT update a workout
router.put('/workouts/running/:id', workoutController.updateRunning); // PUT update a running workout
router.delete('/workouts/:id', workoutController.delete); // DELETE a workout
router.delete('/workouts/running/:id', workoutController.deleteRunning); // DELETE a running workout

export default router;
