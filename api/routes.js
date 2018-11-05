const taskController = require('./task-controller');

module.exports = (app) => {
    app.get('/',(req, res) => res.json('online'));
    
    app.post('/tasks', taskController.create);
    app.get('/tasks', taskController.list);
    app.put('/tasks/:id', taskController.update);
    app.get('/tasks/:id', taskController.detail);
    app.delete('/tasks/:id', taskController.delete);
}