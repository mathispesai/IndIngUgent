import { Request, Response } from 'express';
import {DietService} from "../Services/DietService";


const dietService = new DietService();

export class DietController {
    async getAll(req: Request, res: Response) {
        try {
            const diets = await dietService.getAllDiets();
            res.json(diets);
        } catch (error) {
            res.status(500).send('Error fetching diets');
        }
    }

    async getById(req: Request, res: Response) {
        const { id } = req.params;
        try {
            const diet = await dietService.getDiet(Number(id));
            if (diet) {
                res.json(diet);
            } else {
                res.status(404).send('Diet not found');
            }
        } catch (error) {
            res.status(500).send('Error fetching diet');
        }
    }

    async create(req: Request, res: Response) {
        const { calories, protein, userId } = req.body;
        try {
            await dietService.createDiet(calories, protein, userId);
            res.status(201).send('Diet created successfully');
        } catch (error) {
            res.status(500).send('Error creating diet');
        }
    }

    async update(req: Request, res: Response) {
        const { id } = req.params;
        const { calories, protein } = req.body;
        try {
            await dietService.updateDiet(Number(id), calories, protein);
            res.send('Diet updated successfully');
        } catch (error) {
            res.status(500).send('Error updating diet');
        }
    }

    async delete(req: Request, res: Response) {
        const { id } = req.params;
        try {
            await dietService.deleteDiet(Number(id));
            res.send('Diet deleted successfully');
        } catch (error) {
            res.status(500).send('Error deleting diet');
        }
    }
}
