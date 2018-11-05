const Task = require('./task');

module.exports.create = async (req, res) => {
    console.log(`criando`)
    try {
        console.log(`req.body ${req.body}`)
        const task = new Task(req.body);
        await task.save();    
        res.status(201).json({message: 'Task created!'});
    } catch (error) {
        res.status(500).json(error);
    }
}

module.exports.update = async (req, res) => {
    try {
        let task = await Task.findById(req.params.id);
        if(!task)
            return res.status(404).json({ message: 'Task not found!' });
        
        task.title = req.body.title || task.title;
        task.description = req.body.description || task.description;
        task.done = req.body.done ||  task.done;

        await task.save();
        return res.status(200).json({message: 'Task updated!'});
    } catch(error) {
        return res.status(500).json({'error': error});
    }
}

module.exports.list = async (req, res) => {
    try {
        let tasks = await Task.find({});
        return res.status(200).json(tasks);
    } catch (error) {
        return res.status(500).json(error);
    }
}

module.exports.detail = async (req, res) => {
    try {
        let task = await Task.findById(req.params.id);
        if(!task)
            return res.status(404).json({message: 'Task not found!'});
        return res.status(200).json(task);
    } catch(error) {
        return res.status(500).json(error);
    }
}

module.exports.delete = async (req, res) => {
    try {
        await Task.findByIdAndRemove(req.params.id);
        return res.status(200).json({message: 'Task deleted!'});
    } catch(error) {
        return res.status(500).json(error);
    }
}