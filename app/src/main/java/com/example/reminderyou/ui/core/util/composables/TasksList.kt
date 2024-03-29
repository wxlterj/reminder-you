package com.example.reminderyou.ui.core.util.composables

import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.reminderyou.R
import com.example.reminderyou.domain.model.Task
import com.example.reminderyou.domain.model.TaskWithCategory
import com.example.reminderyou.ui.theme.ReminderYouTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TasksList(
    tasksWithCategory: List<TaskWithCategory>,
    onTaskItemClicked: (TaskWithCategory) -> Unit,
    onTaskChecked: (TaskWithCategory, Boolean) -> Unit,
    onTaskDeleted: (TaskWithCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (tasksWithCategory.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                item {
                    Text(
                        text = stringResource(R.string.to_do),
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
                items(tasksWithCategory, key = { task -> task.task.id }) { tasksWithCategory ->
                    TaskItemSwippable(
                        taskTitle = tasksWithCategory.task.title,
                        taskCategoryName = tasksWithCategory.category?.name ?: "",
                        onTaskItemClicked = { onTaskItemClicked(tasksWithCategory) },
                        isTaskChecked = tasksWithCategory.task.isChecked,
                        onTaskChecked = { isChecked ->
                            onTaskChecked(
                                tasksWithCategory,
                                isChecked
                            )
                        },
                        onTaskDeleted = { onTaskDeleted(tasksWithCategory) },
                        modifier = Modifier.animateItemPlacement()
                    )
                }
            }
        } else {
            NoTasksMessage(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun NoTasksMessage(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.nothing_to_do),
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskItemSwippable(
    taskTitle: String,
    taskCategoryName: String,
    onTaskItemClicked: () -> Unit,
    isTaskChecked: Boolean,
    onTaskChecked: (Boolean) -> Unit,
    onTaskDeleted: () -> Unit,
    modifier: Modifier = Modifier
) {
    val swipeState = rememberDismissState(
        confirmValueChange = {
            if (it == DismissValue.DismissedToStart) {
                onTaskDeleted()
            }
            true
        }
    )

    SwipeToDismiss(
        state = swipeState,
        background = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Red, shape = RoundedCornerShape(12.dp))
            ) {
                Icon(
                    imageVector = Icons.TwoTone.Delete,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 16.dp)
                        .size(40.dp),
                    tint = MaterialTheme.colorScheme.onError
                )
            }
        },
        dismissContent = {
            TaskItem(
                taskTitle = taskTitle,
                categoryName = taskCategoryName,
                onTaskItemClicked = onTaskItemClicked,
                isTaskChecked = isTaskChecked,
                onTaskChecked = onTaskChecked
            )
        },
        modifier = modifier,
        directions = setOf(DismissDirection.EndToStart)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskItem(
    taskTitle: String,
    categoryName: String,
    onTaskItemClicked: () -> Unit,
    isTaskChecked: Boolean,
    onTaskChecked: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp),
        onClick = onTaskItemClicked,
        elevation = CardDefaults.cardElevation(4.dp),
        border = BorderStroke(0.5.dp, Brush.verticalGradient(listOf(Color.Blue, Color.Cyan)))
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = isTaskChecked,
                onCheckedChange = onTaskChecked,
                modifier = Modifier.clip(CircleShape)
            )
            Text(
                text = taskTitle,
                modifier = Modifier.weight(1f),
                textDecoration = if (isTaskChecked) TextDecoration.LineThrough else TextDecoration.None,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge
            )
            if (categoryName.isNotEmpty()) {
                AssistChip(
                    onClick = { /*TODO*/ },
                    label = { Text(text = categoryName) },
                    modifier = Modifier.padding(end = 16.dp),
                    shape = RoundedCornerShape(32.dp)
                )
            }
        }
    }
}