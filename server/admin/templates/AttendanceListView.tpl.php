<?php
	$this->assign('title','ProjectKHEL | Attendances');
	$this->assign('nav','attendances');

	$this->display('_Header.tpl.php');
?>

<script type="text/javascript">
	$LAB.script("scripts/app/attendances.js").wait(function(){
		$(document).ready(function(){
			page.init();
		});
		
		// hack for IE9 which may respond inconsistently with document.ready
		setTimeout(function(){
			if (!page.isInitialized) page.init();
		},1000);
	});
</script>

<div class="container">

<h1>
	<i class="icon-th-list"></i> Attendances
	<span id=loader class="loader progress progress-striped active"><span class="bar"></span></span>
	<span class='input-append pull-right searchContainer'>
		<input id='filter' type="text" placeholder="Search..." />
		<button class='btn add-on'><i class="icon-search"></i></button>
	</span>
</h1>

	<!-- underscore template for the collection -->
	<script type="text/template" id="attendanceCollectionTemplate">
		<table class="collection table table-bordered table-hover">
		<thead>
			<tr>
				<th id="header_Id">Id<% if (page.orderBy == 'Id') { %> <i class='icon-arrow-<%= page.orderDesc ? 'up' : 'down' %>' /><% } %></th>
				<th id="header_HeldOn">Held On<% if (page.orderBy == 'HeldOn') { %> <i class='icon-arrow-<%= page.orderDesc ? 'up' : 'down' %>' /><% } %></th>
				<th id="header_LocationId">Location Id<% if (page.orderBy == 'LocationId') { %> <i class='icon-arrow-<%= page.orderDesc ? 'up' : 'down' %>' /><% } %></th>
				<th id="header_Coordinators">Coordinators<% if (page.orderBy == 'Coordinators') { %> <i class='icon-arrow-<%= page.orderDesc ? 'up' : 'down' %>' /><% } %></th>
				<th id="header_Modules">Modules<% if (page.orderBy == 'Modules') { %> <i class='icon-arrow-<%= page.orderDesc ? 'up' : 'down' %>' /><% } %></th>
				<th id="header_Beneficiaries">Beneficiaries<% if (page.orderBy == 'Beneficiaries') { %> <i class='icon-arrow-<%= page.orderDesc ? 'up' : 'down' %>' /><% } %></th>
				<th id="header_Comment">Comment<% if (page.orderBy == 'Comment') { %> <i class='icon-arrow-<%= page.orderDesc ? 'up' : 'down' %>' /><% } %></th>
				<th id="header_Rating">Rating<% if (page.orderBy == 'Rating') { %> <i class='icon-arrow-<%= page.orderDesc ? 'up' : 'down' %>' /><% } %></th>
<!-- UNCOMMENT TO SHOW ADDITIONAL COLUMNS
				<th id="header_CreatedAt">Created At<% if (page.orderBy == 'CreatedAt') { %> <i class='icon-arrow-<%= page.orderDesc ? 'up' : 'down' %>' /><% } %></th>
				<th id="header_ModifiedAt">Modified At<% if (page.orderBy == 'ModifiedAt') { %> <i class='icon-arrow-<%= page.orderDesc ? 'up' : 'down' %>' /><% } %></th>
				<th id="header_UserSubmitted">User Submitted<% if (page.orderBy == 'UserSubmitted') { %> <i class='icon-arrow-<%= page.orderDesc ? 'up' : 'down' %>' /><% } %></th>
-->
			</tr>
		</thead>
		<tbody>
		<% items.each(function(item) { %>
			<tr id="<%= _.escape(item.get('id')) %>">
				<td><%= _.escape(item.get('id') || '') %></td>
				<td><%if (item.get('heldOn')) { %><%= _date(app.parseDate(item.get('heldOn'))).format('MMM D, YYYY') %><% } else { %>NULL<% } %></td>
				<td><%= _.escape(item.get('locationId') || '') %></td>
				<td><%= _.escape(item.get('coordinators') || '') %></td>
				<td><%= _.escape(item.get('modules') || '') %></td>

				<td><%= _.escape(item.get('beneficiaries') || '') %></td>
				<td><%= _.escape(item.get('comment') || '') %></td>
				<td><%= _.escape(item.get('rating') || '') %></td>
<!-- UNCOMMENT TO SHOW ADDITIONAL COLUMNS
				<td><%if (item.get('createdAt')) { %><%= _date(app.parseDate(item.get('createdAt'))).format('MMM D, YYYY h:mm A') %><% } else { %>NULL<% } %></td>
				<td><%if (item.get('modifiedAt')) { %><%= _date(app.parseDate(item.get('modifiedAt'))).format('MMM D, YYYY h:mm A') %><% } else { %>NULL<% } %></td>
				<td><%= _.escape(item.get('userSubmitted') || '') %></td>
-->
			</tr>
		<% }); %>
		</tbody>
		</table>

		<%=  view.getPaginationHtml(page) %>
	</script>

	<!-- underscore template for the model -->
	<script type="text/template" id="attendanceModelTemplate">
		<form class="form-horizontal" onsubmit="return false;">
			<fieldset>
				<div id="idInputContainer" class="control-group">
					<label class="control-label" for="id">Id</label>
					<div class="controls inline-inputs">
						<span class="input-xlarge uneditable-input" id="id"><%= _.escape(item.get('id') || '') %></span>
						<span class="help-inline"></span>
					</div>
				</div>
				<div id="heldOnInputContainer" class="control-group">
					<label class="control-label" for="heldOn">Held On</label>
					<div class="controls inline-inputs">
						<div class="input-append date date-picker" data-date-format="yyyy-mm-dd">
							<input id="heldOn" type="text" value="<%= _date(app.parseDate(item.get('heldOn'))).format('YYYY-MM-DD') %>" />
							<span class="add-on"><i class="icon-calendar"></i></span>
						</div>
						<span class="help-inline"></span>
					</div>
				</div>
				<div id="locationIdInputContainer" class="control-group">
					<label class="control-label" for="locationId">Location Id</label>
					<div class="controls inline-inputs">
						<input type="text" class="input-xlarge" id="locationId" placeholder="Location Id" value="<%= _.escape(item.get('locationId') || '') %>">
						<span class="help-inline"></span>
					</div>
				</div>
				<div id="coordinatorsInputContainer" class="control-group">
					<label class="control-label" for="coordinators">Coordinators</label>
					<div class="controls inline-inputs">
						<input type="text" class="input-xlarge" id="coordinators" placeholder="Coordinators" value="<%= _.escape(item.get('coordinators') || '') %>">
						<span class="help-inline"></span>
					</div>
				</div>
				<div id="modulesInputContainer" class="control-group">
					<label class="control-label" for="modules">Modules</label>
					<div class="controls inline-inputs">
						<input type="text" class="input-xlarge" id="modules" placeholder="Modules" value="<%= _.escape(item.get('modules') || '') %>">
						<span class="help-inline"></span>
					</div>
				</div>
				<div id="beneficiariesInputContainer" class="control-group">
					<label class="control-label" for="beneficiaries">Beneficiaries</label>
					<div class="controls inline-inputs">
						<input type="text" class="input-xlarge" id="beneficiaries" placeholder="Beneficiaries" value="<%= _.escape(item.get('beneficiaries') || '') %>">
						<span class="help-inline"></span>
					</div>
				</div>
				<div id="commentInputContainer" class="control-group">
					<label class="control-label" for="comment">Comment</label>
					<div class="controls inline-inputs">
						<input type="text" class="input-xlarge" id="comment" placeholder="Comment" value="<%= _.escape(item.get('comment') || '') %>">
						<span class="help-inline"></span>
					</div>
				</div>
				<div id="ratingInputContainer" class="control-group">
					<label class="control-label" for="rating">Rating</label>
					<div class="controls inline-inputs">
						<input type="text" class="input-xlarge" id="rating" placeholder="Rating" value="<%= _.escape(item.get('rating') || '') %>">
						<span class="help-inline"></span>
					</div>
				</div>
				<div id="createdAtInputContainer" class="control-group">
					<label class="control-label" for="createdAt">Created At</label>
					<div class="controls inline-inputs">
						<div class="input-append date date-picker" data-date-format="yyyy-mm-dd">
							<input id="createdAt" type="text" value="<%= _date(app.parseDate(item.get('createdAt'))).format('YYYY-MM-DD') %>" />
							<span class="add-on"><i class="icon-calendar"></i></span>
						</div>
						<div class="input-append bootstrap-timepicker-component">
							<input id="createdAt-time" type="text" class="timepicker-default input-small" value="<%= _date(app.parseDate(item.get('createdAt'))).format('h:mm A') %>" />
							<span class="add-on"><i class="icon-time"></i></span>
						</div>
						<span class="help-inline"></span>
					</div>
				</div>
				<div id="modifiedAtInputContainer" class="control-group">
					<label class="control-label" for="modifiedAt">Modified At</label>
					<div class="controls inline-inputs">
						<div class="input-append date date-picker" data-date-format="yyyy-mm-dd">
							<input id="modifiedAt" type="text" value="<%= _date(app.parseDate(item.get('modifiedAt'))).format('YYYY-MM-DD') %>" />
							<span class="add-on"><i class="icon-calendar"></i></span>
						</div>
						<div class="input-append bootstrap-timepicker-component">
							<input id="modifiedAt-time" type="text" class="timepicker-default input-small" value="<%= _date(app.parseDate(item.get('modifiedAt'))).format('h:mm A') %>" />
							<span class="add-on"><i class="icon-time"></i></span>
						</div>
						<span class="help-inline"></span>
					</div>
				</div>
				<div id="userSubmittedInputContainer" class="control-group">
					<label class="control-label" for="userSubmitted">User Submitted</label>
					<div class="controls inline-inputs">
						<input type="text" class="input-xlarge" id="userSubmitted" placeholder="User Submitted" value="<%= _.escape(item.get('userSubmitted') || '') %>">
						<span class="help-inline"></span>
					</div>
				</div>
			</fieldset>
		</form>

		<!-- delete button is is a separate form to prevent enter key from triggering a delete -->
		<form id="deleteAttendanceButtonContainer" class="form-horizontal" onsubmit="return false;">
			<fieldset>
				<div class="control-group">
					<label class="control-label"></label>
					<div class="controls">
						<button id="deleteAttendanceButton" class="btn btn-mini btn-danger"><i class="icon-trash icon-white"></i> Delete Attendance</button>
						<span id="confirmDeleteAttendanceContainer" class="hide">
							<button id="cancelDeleteAttendanceButton" class="btn btn-mini">Cancel</button>
							<button id="confirmDeleteAttendanceButton" class="btn btn-mini btn-danger">Confirm</button>
						</span>
					</div>
				</div>
			</fieldset>
		</form>
	</script>

	<!-- modal edit dialog -->
	<div class="modal hide fade" id="attendanceDetailDialog">
		<div class="modal-header">
			<a class="close" data-dismiss="modal">&times;</a>
			<h3>
				<i class="icon-edit"></i> Edit Attendance
				<span id="modelLoader" class="loader progress progress-striped active"><span class="bar"></span></span>
			</h3>
		</div>
		<div class="modal-body">
			<div id="modelAlert"></div>
			<div id="attendanceModelContainer"></div>
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" >Cancel</button>
			<button id="saveAttendanceButton" class="btn btn-primary">Save Changes</button>
		</div>
	</div>

	<div id="collectionAlert"></div>
	
	<div id="attendanceCollectionContainer" class="collectionContainer">
	</div>

	<p id="newButtonContainer" class="buttonContainer">
		<button id="newAttendanceButton" class="btn btn-primary">Add Attendance</button>
	</p>

</div> <!-- /container -->

<?php
	$this->display('_Footer.tpl.php');
?>
