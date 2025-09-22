import { Request, Response } from 'express';
import {WorkoutService} from "../Services/workoutService";


const workoutService = new WorkoutService();

export class WorkoutController {
    async getAll(req: Request, res: Response) {
        try {
            const workouts = await workoutService.getAllWorkouts();
            res.json(workouts);
        } catch (error) {
            res.status(500).send('Error fetching workouts');
        }
    }

    async getById(req: Request, res: Response) {
        const { id } = req.params;
        try {
            const workout = await workoutService.getWorkout(Number(id));
            if (workout) {
                res.json(workout);
            } else {
                res.status(404).send('Workout not found');
            }
        } catch (error) {
            res.status(500).send('Error fetching workout');
        }
    }

    async create(req: Request, res: Response) {
        const { type, duration_minutes, date, userId } = req.body;
        try {
            await workoutService.createWorkout(type, duration_minutes, date, userId);
            res.status(201).send('Workout created successfully');
        } catch (error) {
            res.status(500).send('Error creating workout');
        }
    }

    async createRunning(req: Request, res: Response) {
        const { type, duration_minutes, date, distance, pace, userId } = req.body;
        try {
            await workoutService.createRunningWorkout(type, duration_minutes, date, distance, pace, userId);
            res.status(201).send('Running workout created successfully');
        } catch (error) {
            res.status(500).send('Error creating running workout');
        }
    }

    async update(req: Request, res: Response) {
        const { id } = req.params;
        const { type, duration_minutes, date } = req.body;
        try {
            await workoutService.updateWorkout(Number(id), type, duration_minutes, date);
            res.send('Workout updated successfully');
        } catch (error) {
            res.status(500).send('Error updating workout');
        }
    }

    async updateRunning(req: Request, res: Response) {
        const { id } = req.params;
        const { type, duration_minutes, date, distance, pace } = req.body;
        try {
            await workoutService.updateRunningWorkout(Number(id), type, duration_minutes, date, distance, pace);
            res.send('Running workout updated successfully');
        } catch (error) {
            res.status(500).send('Error updating running workout');
        }
    }

    async delete(req: Request, res: Response) {
        const { id } = req.params;
        try {
            await workoutService.deleteWorkout(Number(id));
            res.send('Workout deleted successfully');
        } catch (error) {
            res.status(500).send('Error deleting workout');
        }
    }

    async deleteRunning(req: Request, res: Response) {
        const { id } = req.params;
        try {
            await workoutService.deleteRunningWorkout(Number(id));
            res.send('Running workout deleted successfully');
        } catch (error) {
            res.status(500).send('Error deleting running workout');
        }
    }
}
