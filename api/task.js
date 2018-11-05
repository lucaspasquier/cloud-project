const mongoose = require('mongoose');
module.exports = mongoose.model('Task', { title: String, description: String, done: Boolean });