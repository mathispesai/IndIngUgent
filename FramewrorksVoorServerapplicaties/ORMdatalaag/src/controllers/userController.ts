import { Request, Response } from 'express';
import {UserService} from "../Services/userService";


const userService = new UserService();

export class UserController {
    async getAll(req: Request, res: Response) {
        try {
            const users = await userService.getAllUsers();
            res.json(users);
        } catch (error) {
            res.status(500).send('Error fetching users');
        }
    }

    async getById(req: Request, res: Response) {
        const { id } = req.params;
        try {
            const user = await userService.getUser(Number(id));
            if (user) {
                res.json(user);
            } else {
                res.status(404).send('User not found');
            }
        } catch (error) {
            res.status(500).send('Error fetching user');
        }
    }

    async create(req: Request, res: Response) {
        const { firstName, lastName, age } = req.body;
        try {
            await userService.createUser(firstName, lastName, age);
            res.status(201).send('User created successfully');
        } catch (error) {
            res.status(500).send('Error creating user');
        }
    }

    async update(req: Request, res: Response) {
        const { id } = req.params;
        const { firstName, lastName, age } = req.body;
        try {
            await userService.updateUser(Number(id), firstName, lastName, age);
            res.send('User updated successfully');
        } catch (error) {
            res.status(500).send('Error updating user');
        }
    }

    async delete(req: Request, res: Response) {
        const { id } = req.params;
        try {
            await userService.deleteUser(Number(id));
            res.send('User deleted successfully');
        } catch (error) {
            res.status(500).send('Error deleting user');
        }
    }
    async addFriend(req: Request, res: Response) {
        try {
            const { userId, friendId } = req.body;
            const user = await userService.addFriend(userId, friendId);
            res.json(user);
        } catch (error) {
            res.status(400).json({ message: error.message });
        }
    }
}
