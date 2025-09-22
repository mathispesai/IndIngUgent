import { DAO } from '../DAO';
import { Diet } from '../entity/Diet';
import { AppUser } from '../entity/AppUser';

const dao = new DAO();

export class DietService {
    async getAllDiets() {
        return await dao.getAllDiets();
    }

    async getDiet(id: number) {
        return await dao.getDiet(id);
    }

    async getDietForUser(userId: number) {
        return await dao.getDietFromAppUser(userId);
    }

    async createDiet(calories: number, protein: number, userId: number) {
        const appUser = await dao.getAppUser(userId);
        if (!appUser) throw new Error('User not found');
        await dao.setDiet(calories, protein, appUser);
    }

    async updateDiet(id: number, calories: number, protein: number) {
        await dao.updateDiet(id, calories, protein);
    }

    async deleteDiet(id: number) {
        await dao.deleteDiet(id);
    }
}