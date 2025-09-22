import { DAO } from '../DAO';
import { Workout } from '../entity/Workout';
import { Running } from '../entity/Running';

const dao = new DAO();

export class WorkoutService {
    async getAllWorkouts() {
        return await dao.getAllWorkouts();
    }

    async getWorkout(id: number) {
        return await dao.getWorkout(id);
    }

    async createWorkout(type: string, duration_minutes: number, date: Date, userId: number) {
        const appUser = await dao.getAppUser(userId);
        if (!appUser) throw new Error('User not found');
        await dao.setWorkout(type, duration_minutes, date, appUser);
    }

    async createRunningWorkout(type: string, duration_minutes: number, date: Date, distance: number, pace: number, userId: number) {
        const appUser = await dao.getAppUser(userId);
        if (!appUser) throw new Error('User not found');
        await dao.setRunningWorkout(type, duration_minutes, date, distance, pace, appUser);
    }

    async updateWorkout(id: number, type: string, duration_minutes: number, date: Date) {
        await dao.updateWorkout(id, type, duration_minutes, date);
    }

    async updateRunningWorkout(id: number, type: string, duration_minutes: number, date: Date, distance: number, pace: number) {
        await dao.updateRunningWorkout(id, type, duration_minutes, date, distance, pace);
    }

    async deleteWorkout(id: number) {
        await dao.deleteWorkout(id);
    }

    async deleteRunningWorkout(id: number) {
        await dao.deleteRunning(id);
    }
}
